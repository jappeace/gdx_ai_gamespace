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


package nl.jappieklooster.gdx.mapstare.model.math
import com.badlogic.gdx.math.MathUtils

/**
  * Immutable circle
  * @param position
  * @param radius
  */
case class Circle(position: Point, radius:Float) {
	def contains(point: Point) = {
		val x = position.x - point.x
		val y = position.y - point.y
		x * x + y * y <= radius * radius
	}

	def contains(c: Circle): Boolean = {
		val radiusDiff: Float = radius - c.radius
		if (radiusDiff < 0f) return false
		val dx: Float = position.x - c.position.x
		val dy: Float = position.y - c.position.y
		val dst: Float = dx * dx + dy * dy
		val radiusSum: Float = radius + c.radius
		!(radiusDiff * radiusDiff < dst) && (dst < radiusSum * radiusSum)
	}

	def overlaps(c: Circle): Boolean = {
		val dx: Float = position.x - c.position.x
		val dy: Float = position.y - c.position.y
		val distance: Float = dx * dx + dy * dy
		val radiusSum: Float = radius + c.radius
		distance < radiusSum * radiusSum
	}

	lazy val circumference: Float = this.radius * MathUtils.PI2
	lazy val area: Float = this.radius * this.radius * MathUtils.PI
}
