package nl.jappieklooster.gdx.mapstare.akka

import akka.actor.Actor
import org.slf4j.LoggerFactory

class ThreadIdentifyActor extends Actor{
	val log = LoggerFactory.getLogger(classOf[ThreadIdentifyActor])
	override def receive: Receive = {
		case _ => log.info(ThreadIdentifier.identify)
	}
}
object ThreadIdentifier{
	def identify:String =s"threadID ${Thread.currentThread().getId}"
}
