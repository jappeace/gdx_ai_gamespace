package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input._

import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.model.Direction._

class CamController(implicit cam:Cam) extends NotAnInputProcessor{
	override def keyTyped(char:Char):Boolean = char match {
		case 'a' => cam.move(Left); false
		case 'd' => cam.move(Right); false
		case 'w' => cam.move(Up); false
		case 's' => cam.move(Down); false
		case _ => false
	}
}
