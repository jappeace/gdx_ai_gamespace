package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.{InputProcessor, InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{MoveTo, Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input._
import nl.jappieklooster.gdx.mapstare.model.{Individual, World, GameTick}
import nl.jappieklooster.gdx.mapstare.model.math._
import nl.jappieklooster.gdx.mapstare.view.{Renderable, Animation}
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
	val clickHandler = new MouseClickAdapter {
		override def leftClick(screenX:Int, screenY:Int, pointer:Int) = {
			true
		}
		override def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			world.units = world.units.map(x=> if(x.selected){
				x.copy(
					controller = MoveTo(cam.screenToTile(cam.unproject(Point(screenX,screenY))).topLeftPixels)
				)
			}else x)
			true
		}
	}
	override def enter(gameState: StateMachine): Unit ={
		game.customRenders = game.customRenders :+ new Renderable {
			var x = 0D
			override def render(spriteBatch: SpriteBatch): Unit = {
				x += 0.02D
				Animation.line.setPosition(200 ,300+ (Math.sin(x) *50).toInt)
				Animation.line.draw(spriteBatch)
			}
		}
		game.selectionController.callback = SelectionBox.markUnitsAsSelected(game.world)
		inputMultiplexer.addProcessor(clickHandler)
	}
	override def exit():Unit = {
		inputMultiplexer.removeProcessor(clickHandler)
	}
}
