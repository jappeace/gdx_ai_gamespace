package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input._
import com.badlogic.gdx.math.Vector2

import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.IntervalledUpdatable
import nl.jappieklooster.gdx.mapstare.model.Direction._
import nl.jappieklooster.gdx.mapstare.model.GameTick

class CamMovement(implicit cam:Cam) extends IntervalledUpdatable with InputAdapter{
	setFrametime(0.06f)
	var movement = Vector2.Zero.cpy
	override def keyDown(keycode:Int):Boolean = keycode match {
		case Keys.A | Keys.LEFT => movement.add(Left); true
		case Keys.D | Keys.RIGHT => movement.add(Right); true
		case Keys.W | Keys.UP => movement.add(Up); true
		case Keys.S | Keys.DOWN => movement.add(Down); true
		case _ => false
	}
	override def keyUp(keycode:Int):Boolean = keycode match {
		case Keys.A | Keys.LEFT => movement.sub(Left); true
		case Keys.D | Keys.RIGHT => movement.sub(Right); true
		case Keys.W | Keys.UP => movement.sub(Up); true
		case Keys.S | Keys.DOWN => movement.sub(Down); true
		case _ => false
	}
	override def intervalledUpdate(timeSinceLast:GameTick): Boolean={
		cam.move(movement)
		movement = Vector2.Zero.cpy()
		true
	}
}
