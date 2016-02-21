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
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import nl.jappieklooster.gdx.mapstare.model.math.Tile
import nl.jappieklooster.gdx.mapstare.model.{Individual, Entity}
import nl.jappieklooster.gdx.mapstare.model.math.Point
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.view.Animation
import nl.jappieklooster.gdx.mapstare.Cam


class PlacementClick(placeCallback:(Individual)=>Unit, cam:Cam, plexer:InputMultiplexer) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		plexer.addProcessor(processor)
	}
	val processor = new MouseClickAdapter {
		override def leftClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			placeCallback(
				Individual(
					screenToTile(
						new Vector3(
							screenX,
							Gdx.graphics.getHeight - screenY,
							0
						)
					).topLeftPixels
				)
			)
			true
		}
		override def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			plexer.removeProcessor(this)
			true
		}
		}

	def screenToTile(screen:Vector3) = Tile.fromPixels(screen) + cam.getPosition - Tile(3,4)
}
