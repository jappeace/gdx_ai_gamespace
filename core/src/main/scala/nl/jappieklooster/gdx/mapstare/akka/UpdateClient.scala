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

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import akka.util.ByteString
import nl.jappieklooster.gdx.mapstare.{Logging, Game}
import nl.jappieklooster.gdx.mapstare.model.{WorldState, World}

import scala.pickling.Defaults._
import scala.pickling.json._
import akka.io._

/**
  * This guy should run on the same thread as the render loop and updates
  * the worldstate. It receives the world (or any other state) and makes
  * the game awere of it.
  *
  * @param game
  */
class UpdateClient(game:Game) extends Actor with Logging{
	IO(Udp)(context.system) ! Udp.Bind(self, UpdateClient.localhost)
  def receive = {
    case Udp.Bound(local) =>
      context.become(ready(sender()))
  }

  def ready(socket: ActorRef): Receive = {
    case Udp.Received(data, remote) =>
		val json = data.map(_.toChar).mkString
		try {
			game.world = json.unpickle[WorldState]
		}catch{
			case c:ClassCastException =>
				log.info(s"couldn't cast: $json")
				throw c
			case x:Throwable => throw x
		}
    case Udp.Unbind  => socket ! Udp.Unbind
    case Udp.Unbound => context.stop(self)
  }
}
object  UpdateClient{
	val name = "updateClient"
	val localhost = new InetSocketAddress("localhost", 2554)
}
