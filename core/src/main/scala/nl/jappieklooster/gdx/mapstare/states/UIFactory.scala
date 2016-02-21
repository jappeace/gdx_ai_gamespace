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


package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui._

/**
  * share skin and stylename across ui elements
  */
class UIFactory(skin:Skin = new Skin(Gdx.files.internal("uiskin.json")), styleName:String = "default") {
	def table() = new Table(skin)
	def scrollPane() = {
		val scrolltable = table()
		val scrollpane = new ScrollPane(scrolltable, skin, styleName)
		(scrolltable, scrollpane)
	}
	def button(text:String) = new TextButton(text, skin, styleName)
	def textField(value:String) = new TextField(value, skin)
	def dialog(title:String) = new Dialog(title, skin)

}
