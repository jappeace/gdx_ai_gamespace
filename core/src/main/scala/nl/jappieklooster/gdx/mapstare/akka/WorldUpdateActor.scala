// This is a playground for AI, it offers a game space for AI to work with.
// Copyright (C) 2016 Jappe Klooster

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.If not, see <http://www.gnu.org/licenses/>.


package nl.jappieklooster.gdx.mapstare.akka

import java.net.InetSocketAddress

import akka.actor._
import akka.actor.Actor.Receive
import nl.jappieklooster.gdx.mapstare.Logging
import nl.jappieklooster.gdx.mapstare.controller.{MoveTo, WorldUpdater}
import nl.jappieklooster.gdx.mapstare.input.SelectionBox
import nl.jappieklooster.gdx.mapstare.model._
import nl.jappieklooster.gdx.mapstare.model.math.Rectangle
import org.slf4j.LoggerFactory

/**
  * This actor is boss of the world.
  *
  * If you want to change the world you go to him. If you want to verify your
  * local information about the world, you go to him.
  */
class WorldUpdateActor extends Actor with Logging{
	val broadcaster = context.system.actorOf(Props[WorldBroadcastActor])
	val world = new World()
	val updater = new WorldUpdater(world)
	private var started = false
	private var lastTime = 0L
	override def receive: Receive = {
		case WorldUpdateActor.Start =>
			started = true
			lastTime = System.currentTimeMillis()
			self ! Update

		case WorldUpdateActor.Stop =>
			started = false

		// We received a tick, update the world and the latest state
		case Update =>
			if(!started){
				log.info("STOPPED! Ignoring gametick")
			}else{
				val difference = System.currentTimeMillis() - lastTime

				// we require a minimum reasonable update to give the desirializers
				// time to catch up. Turns out that with small worlds those are
				// the slow parts.
				if(difference > WorldUpdateActor.minimumReasonableUpdate) {
					lastTime += difference
					val tick = GameTick(difference/1000)

					updater.update(tick)

					// It is possible to send the entire state, because the gametick
					// message should *not* be send over a network.
					val newWorld = updater.world.copy()
					broadcaster ! BroadCast(lastTime, newWorld)
				}
				self ! Update
			}

		case Create(unit:Positionable) =>
			log.info(s"creating $unit")
			world.insert(unit)

		case box:Rectangle => SelectionBox.markUnitsAsSelected(updater.world)(box)
		case command:MoveTo =>
			log.info(s"executing $command")
			updater.world.mapUnits(x=> if(x.selected){
				x.copy(
					controller = command.copy()
				)
			}else x)
		case x:RegisterUpdateClient => broadcaster ! x
		case _ => log.warn(s"received unkown message")
	}
	private case object Update
}
object WorldUpdateActor{
	case object Start
	case object Stop
	val minimumReasonableUpdate = 1000/60 //ms
}
case class Create[T <: Positionable](what:T)
case class RegisterUpdateClient(name:InetSocketAddress)
