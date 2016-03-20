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


package nl.jappieklooster.gdx.mapstare.akka.client

import java.net.{DatagramSocket, InetSocketAddress, ServerSocket}

import akka.actor.{Props, Actor, ActorRef}
import akka.dispatch.BoundedMessageQueueSemantics
import akka.io._
import nl.jappieklooster.gdx.mapstare.akka.Serializer
import nl.jappieklooster.gdx.mapstare.model.WorldState
import nl.jappieklooster.gdx.mapstare.{Game, Logging}


import akka.dispatch.RequiresMessageQueue
import akka.dispatch.BoundedMessageQueueSemantics

/**
  * This guy should run on the same thread as the render loop and updates
  * the worldstate. It receives the world (or any other state) and makes
  * the game awere of it.
  *
  * @param game
  */
class WorldStateUDPDeserializeActor(game:Game) extends Actor with Logging with RequiresMessageQueue[BoundedMessageQueueSemantics] {
	IO(Udp)(context.system) ! Udp.Bind(self, WorldStateUDPDeserializeActor.localhost)
	val gameThreadSyncer = context.actorOf(Props(new WorldStateToGameActor(game)).withDispatcher("gdx-dispatcher"))
	def receive = {
		case Udp.Bound(local) =>
			context.become(ready(sender()))
	}

	def ready(socket: ActorRef): Receive = {
		case Udp.Received(data, remote) =>
			gameThreadSyncer ! Serializer.deserialize[WorldState](data)
		case Udp.Unbind  => socket ! Udp.Unbind
		case Udp.Unbound => context.stop(self)
	}
}
object  WorldStateUDPDeserializeActor{
	val name = "updateClient"
	val minPort = 1100
	val maxPort = 49151
	val localhost = {

		var port = minPort
		while(!available(port)) {
			port += 1
		}
		new InetSocketAddress("localhost", port)
	}
	/**
	  * Checks to see if a specific port is available.
	  *
	  * @param port the port to check for availability
	  */
	def available(port:Int):Boolean = {
		if (port < minPort || port > maxPort){
			return false
		}
		import java.io.IOException;
		var ss:Option[ServerSocket] = None
		var ds:Option[DatagramSocket] = None
		try {
			ss = Option(new ServerSocket(port))
			for(rs <- ss){
				rs.close()
			}
			ds = Option(new DatagramSocket(port))
			for(dr <- ds){
				dr.close()
			}
			return true;
		} catch {
			case e:IOException =>
				for(rs <- ss){
					rs.close()
				}
				for(dr <- ds){
					dr.close()
				}
			case t:Throwable => throw t
		}
		false
	}
}
