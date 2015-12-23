package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input._

import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.model.Direction._

class CamController(implicit cam:Cam) extends NotAnInputProcessor{
	override def keyDown(keycode: Int): Boolean = keycode match {
		case Keys.LEFT | Keys.A  => cam.move(Left); true
		case Keys.RIGHT | Keys.D => cam.move(Right); true
		case Keys.UP | Keys.W => cam.move(Up); true
		case Keys.DOWN | Keys.S => cam.move(Down); true
		case _ => false
	}
}
