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