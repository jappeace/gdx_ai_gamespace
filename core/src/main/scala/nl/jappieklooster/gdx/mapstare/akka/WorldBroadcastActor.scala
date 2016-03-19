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

import akka.actor.{ActorRef, ActorPath, ActorSelection, Actor}
import akka.io._
import nl.jappieklooster.gdx.mapstare.Logging
import nl.jappieklooster.gdx.mapstare.model.{Entity, WorldState, World}
/**
  * This the actor that keeps shouting the world state to everyone.
  * So it receives a simple world state from another actor and will send it
  * to *everyone* it knows. Its like that annoying facebook friend that
  * keeps reposting shit.
  */
class WorldBroadcastActor extends Actor with Logging{
	private var updateClients:Seq[InetSocketAddress] = Nil

	IO(Udp)(context.system) ! Udp.SimpleSender
	override def receive: Receive = {
		case Udp.SimpleSenderReady =>
			log.info("starting with sending")
			context.become(broadcasting(sender()))
		case x:RegisterUpdateClient =>
			// handle once ready
			context.self ! x

	}
	def broadcasting(socket:ActorRef):Receive = {
		case world:WorldState =>
			val message = Serializer.serialize(world)
			for(client <- updateClients){
				socket ! Udp.Send(message, client)
			}
		case RegisterUpdateClient(path) =>
			log.info(s"registering $path")
			updateClients = path +: updateClients
	}
}
