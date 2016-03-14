package nl.jappieklooster.gdx.mapstare.akka

import java.net.InetSocketAddress

import akka.actor.Actor.Receive
import akka.actor.{ActorRef, ActorPath, ActorSelection, Actor}
import akka.io._
import akka.util.ByteString
import nl.jappieklooster.gdx.mapstare.Logging
import nl.jappieklooster.gdx.mapstare.model.math.Tile
import nl.jappieklooster.gdx.mapstare.model.{Entity, WorldState, World}
import org.slf4j.LoggerFactory
import scala.pickling.Defaults._
import scala.pickling.json._
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
			val message = ByteString(world.pickle.value)
			for(client <- updateClients){
				socket ! Udp.Send(message, client)
			}
		case RegisterUpdateClient(path) =>
			log.info(s"registering $path")
			updateClients = path +: updateClients
	}
}
