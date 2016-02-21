package nl.jappieklooster.gdx.mapstare.akka
import akka.actor.Actor
import akka.actor.Actor.Receive
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.model.{WorldState, World}

class UpdateClient(game:Game) extends Actor{

	override def receive: Receive = {
		case world:WorldState => game.world = world
	}
}
object  UpdateClient{
	val name = "updateClient"
}
