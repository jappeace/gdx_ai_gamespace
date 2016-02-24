package nl.jappieklooster.gdx.mapstare.akka

import akka.actor.{ScalaActorRef, ActorRef}
import org.slf4j.LoggerFactory

/**
  * Place holder for where should be an actor (its a fancy option with logging)
  */
class ActorFacade{
	var actor:Option[ActorRef] = None
	val log = LoggerFactory.getLogger(classOf[ActorFacade])
	def !(message:Any) = actor match{
		case Some(x) => x ! message
		case None => log.warn(s"message $message not send")
	}
}
