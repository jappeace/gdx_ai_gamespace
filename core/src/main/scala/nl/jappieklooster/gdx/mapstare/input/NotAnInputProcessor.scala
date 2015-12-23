package nl.jappieklooster.gdx.mapstare.input
import com.badlogic.gdx.InputProcessor;

/**
 * Just implement everyting with false so other processers have less boilerplate
 */
class NotAnInputProcessor extends InputProcessor{
	override def keyTyped(character: Char): Boolean = false
	override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
	override def keyDown(keycode: Int): Boolean = false
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override def keyUp(keycode: Int): Boolean = false
	override def scrolled(amount: Int): Boolean = false
	override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
	override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}
