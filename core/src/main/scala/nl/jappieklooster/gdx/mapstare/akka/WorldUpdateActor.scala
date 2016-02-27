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
import akka.actor.{ActorPath, ActorSelection, Actor}
import akka.actor.Actor.Receive
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
class WorldUpdateActor extends Actor{
	val world = new World()
	val updater = new WorldUpdater(world)
	val log = LoggerFactory.getLogger(classOf[WorldUpdateActor])
	private var updateClients:Seq[ActorSelection] = Nil
	override def receive: Receive = {
		// We received a tick, update the world and the latest state
		case tick:GameTick =>
			updater.update(tick)

			// It is possible to send the entire state, because the gametick
			// message should *not* be send over a network.
			val newWorld = updater.world.copy()

			for(client <- updateClients){
				client ! newWorld
			}

		case RegisterUpdateClient(path) =>
			val select = context.actorSelection(path)
			updateClients = select +: updateClients

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
		case _ => log.warn(s"received unkown message")
	}
}
case class Create[T <: Positionable](what:T)
case class RegisterUpdateClient(name:String)
object RegisterUpdateClient{
	val hosttext = "clientip"
	val defaultHost = "0.0.0.0"
}
