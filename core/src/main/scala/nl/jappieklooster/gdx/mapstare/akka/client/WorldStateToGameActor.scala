package nl.jappieklooster.gdx.mapstare.akka.client

import akka.actor.Actor.Receive
import nl.jappieklooster.gdx.mapstare.{Game, Logging}
import akka.actor.{Actor, ActorRef}
import nl.jappieklooster.gdx.mapstare.model.WorldState

class WorldStateToGameActor(game:Game)extends Actor with Logging{
	override def receive: Receive = {
		case x:WorldState =>
			game.world = x
	}
}
