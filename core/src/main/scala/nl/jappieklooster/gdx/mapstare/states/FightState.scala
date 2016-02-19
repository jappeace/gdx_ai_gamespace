package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.{InputProcessor, InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{MoveTo, Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input._
import nl.jappieklooster.gdx.mapstare.model.{World, GameTick}
import nl.jappieklooster.gdx.mapstare.model.math._
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
		log.info("FIGHTING@!")
		true
	}
	val clickHandler = new MouseClickAdapter {
		override def leftClick(screenX:Int, screenY:Int, pointer:Int) = {
			true
		}
		override def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			world.units = world.units.map(x=> if(x.selected){x.copy(controller = MoveTo(Circle(cam.screenPointToWorld(Point(screenX,screenY)), 0.1f)))}else{x})
			true
		}
	}
	override def enter(gameState: StateMachine): Unit ={
		game.selectionController.callback = SelectionBox.markUnitsAsSelected(game.world)
		inputMultiplexer.addProcessor(clickHandler)
	}
	override def exit():Unit = {
		inputMultiplexer.removeProcessor(clickHandler)
	}
}
