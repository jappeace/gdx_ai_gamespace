package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.InputProcessor;

/**
 * Just implement everyting with false so other processers have less boilerplate
 */
trait InputAdapter extends InputProcessor{
	override def keyTyped(character: Char): Boolean = false
	override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
	override def keyDown(keycode: Int): Boolean = false
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override def keyUp(keycode: Int): Boolean = false
	override def scrolled(amount: Int): Boolean = false
	override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}

trait MouseClickAdapter extends InputAdapter {
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		button match {
			case Buttons.LEFT => leftClick(screenX, screenY, pointer)
			case Buttons.RIGHT => rightClick(screenX, screenY, pointer)
			case _ => false
		}
	}
	def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = false
	def leftClick(screenX:Int, screenY:Int, pointer:Int):Boolean = false
}