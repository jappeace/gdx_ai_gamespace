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
import akka.actor.Actor
import akka.actor.Actor.Receive
import nl.jappieklooster.gdx.mapstare.controller.WorldUpdater
import nl.jappieklooster.gdx.mapstare.model._
import org.slf4j.LoggerFactory

/**
  * This actor is boss of the world.
  *
  * If you want to change the world you go to him. If you want to verify your
  * local information about the world, you go to him.
  */
class WorldUpdateActor extends Actor{
	val updater = new WorldUpdater(new World())
	val log = LoggerFactory.getLogger(classOf[WorldUpdateActor])
	override def receive: Receive = {
		// We received a tick, update the world and the latest state
		case tick:GameTick =>
			updater.update(tick)

			// It is possible to send the entire state, because the gametick
			// message should *not* be send over a network.
			val newWorld = updater.world.copy()
			context.actorSelection("../"+UpdateClient.name) ! newWorld
		case Create(unit:Individual) =>
			updater.world.units += unit
			log.info(s"creating $unit")
		case Create(ent:Entity) => updater.world.entities += ent
		case Update(id:Int, unit:Individual) => updater.world.units(id) = unit
		case Update(id:Int, ent:Entity) => updater.world.entities(id) = ent
		case _ => log.warn(s"received unkown message")
	}
}
case class Create[T <: Positionable](what:T)
case class Update[T <: Positionable](id:Int, to:T)
// TODO: delete, use a case class with an int to identitfy which, then push
// that int on a stack, then on antoher create use that int to update the
// propper reference. Just make sure that the reference is marked dead before
// deleting (so we draw a dead animation that is not controlable instead of
// a living unit).