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


package nl.jappieklooster.gdx.mapstare.input.gui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


/**
 * A less verbose click listener
 * @param function
 */
class OnClick(function: (InputEvent, Float,Float) => Unit) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		function(event,x,y)
	}
}
object OnClick{
	/**
	 * the allows passing of a function that doesn't care about arguments
	 * (most common use case probably)
	 * @param function
	 * @return
	 */
	def apply(function: =>Unit):OnClick=apply((a,b,c)=>function)
	def apply(function:(InputEvent)=>Unit):OnClick=apply((a, b,c)=>function(a))
	def apply(function: (InputEvent, Float,Float) => Unit)=new OnClick(function)
}
