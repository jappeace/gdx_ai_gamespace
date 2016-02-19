package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.{InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input.{PlacementClick, SelectionBox, CamMovement}
import nl.jappieklooster.gdx.mapstare.model.{World, GameTick}
import org.slf4j.LoggerFactory
import nl.jappieklooster.gdx.mapstare.Game

class FightState (game:Game) extends GameState(game){
	val log = LoggerFactory.getLogger(classOf[FightState])
	/**
	  *
	  * @param tick
	  * @return should keep updating?
	  */
	override def update(tick: GameTick): Boolean = {
		true
	}
	override def enter(gameState: StateMachine): Unit ={
		game.selectionController.callback = SelectionBox.markUnitsAsSelected(game.world)
	}
}
