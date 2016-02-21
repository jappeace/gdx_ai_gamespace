package nl.jappieklooster.gdx.mapstare.states

import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.model.GameTick

/**
  * Connect or start a server
  */
class ConnectState(game:Game) extends GameState(game){
	override def enter(stateMachine: StateMachine):Unit = {
		val factory = new UIFactory()
	}
}
