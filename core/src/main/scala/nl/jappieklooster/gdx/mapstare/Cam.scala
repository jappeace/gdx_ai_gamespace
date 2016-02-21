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


package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import nl.jappieklooster.gdx.mapstare.model._
import nl.jappieklooster.gdx.mapstare.model.math._
import Direction._
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.model.math.Tile

/**
 * Tha camera
 */
class Cam {
	val cam = new OrthographicCamera()
	private var position = Tile.zero
	private var positionChange = Tile.zero
	toOrtho(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
	def getPosition = position
	def move(direction: Point): Unit ={
		positionChange = Tile.fromVector(direction + Tile.toPoint(positionChange))
		position += positionChange
		cam.translate(positionChange.topLeftPixels)
	}
	def pointToScreenPixels(tile:Point) :Point = {
		Tile.toPixels(tile) - position.topLeftPixels
	}

	def mouseScreenPos():Vector3 = unproject(
		new Vector3(Gdx.input.getX, Gdx.input.getY,0)
	)

	def screenToTile(screen:Point) = Tile.fromPixels(screen) + getPosition - Tile(3,4)
	def screenToTile(screen:Vector3) = Tile.fromPixels(screen) + getPosition - Tile(3,4)
	def screenPointToWorld(point:Point):Point = {
		val tile = Tile.fromPixels(point) + position - Tile(3,4)
		val result = cam.project(new Vector3(tile.topLeftPixels.x,tile.topLeftPixels.y,0))
		Point(result.x,result.y)
	}
	def unproject(vector2: Point):Point= {
		val v3 = unproject(new Vector3(vector2, 0))
		Point(v3.x, v3.y)
	}
	private def unproject(vector: Vector3) = cam.unproject(
		vector.sub(
			new Vector3(
				Tile.toPixels(Tile.toPoint(position)).scale(1,-1),
				0
			)
		)
	)

	def toOrtho(width:Float, height:Float) = {
		cam.setToOrtho(false, width, height)
		cam.update()
		position = Tile.zero
	}

}

object Cam {
	implicit val cam = new Cam()
}