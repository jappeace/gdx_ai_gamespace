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


package nl.jappieklooster.gdx.mapstare.akka.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef}
import akka.io._
import akka.util.ByteString
import nl.jappieklooster.gdx.mapstare.Logging
import nl.jappieklooster.gdx.mapstare.akka.Serializer
import nl.jappieklooster.gdx.mapstare.model.WorldState

/**
  * This the actor that keeps shouting the world state to everyone.
  * So it receives a simple world state from another actor and will send it
  * to *everyone* it knows. Its like that annoying facebook friend that
  * keeps reposting shit.
  */
class WorldBroadcastActor extends Actor with Logging{
	private var updateClients:Seq[InetSocketAddress] = Nil

	IO(Udp)(context.system) ! Udp.SimpleSender
	private var lastSend = ByteString.empty
	override def receive: Receive = {
		case Udp.SimpleSenderReady =>
			log.info("starting with sending")
			context.become(broadcasting(sender()))
		case x:RegisterUpdateClient =>
			// handle once ready
			context.self ! x

	}
	def broadcasting(socket:ActorRef):Receive = {
		case BroadCast(sendTime, world) =>
			// drop ancient messages
			val relevance = sendTime + WorldBroadcastActor.mimimunRelevance
			val now = System.currentTimeMillis()
			if(relevance > now) {

				val message = Serializer.serialize(world)
				if (message != lastSend) { // drop duplicates

					lastSend = message
					for (client <- updateClients) {
						socket ! Udp.Send(message, client)
					}
				}
			}
		case RegisterUpdateClient(path) =>
			log.info(s"registering $path")
			updateClients = path +: updateClients

	}
}
object WorldBroadcastActor{
	val mimimunRelevance = 2
}
case class BroadCast(sendTime:Long, world:WorldState)