package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input

import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.model.Direction._

class CamController(implicit cam:Cam) extends NotAnInputProcessor{
	override def keyDown(keycode: Int): Boolean = keycode match {
		case Input.Keys.LEFT => cam.move(Left); true
		case Input.Keys.RIGHT => cam.move(Right); true
		case Input.Keys.UP => cam.move(Up); true
		case Input.Keys.DOWN => cam.move(Down); true
		case _ => false
	}
}
