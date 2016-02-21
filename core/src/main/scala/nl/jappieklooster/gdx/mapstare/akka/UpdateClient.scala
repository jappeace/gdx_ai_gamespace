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
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.model.{WorldState, World}

/**
  * This guy should run on the same thread as the render loop and updates
  * the worldstate. It receives the world (or any other state) and makes
  * the game awere of it.
  * @param game
  */
class UpdateClient(game:Game) extends Actor{

	override def receive: Receive = {
		case world:WorldState => game.world = world
	}
}
object  UpdateClient{
	val name = "updateClient"
}
