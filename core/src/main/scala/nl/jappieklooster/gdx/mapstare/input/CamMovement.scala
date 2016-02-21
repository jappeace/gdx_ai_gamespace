// This is a playground for AI, it offers a game space for AI to work with.
// Copyright (C) 2016 Jappe Klooster

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.If not, see <http://www.gnu.org/licenses/>.


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
