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
case class Rectangle(position:Point, width:Float, height:Float) {

	val x = position.x
	val y = position.y
	def contains(point:Point) = x <= point.x && x + width >= point.x && y <= point.y && y + height >= point.y
}
object Rectangle{
	/**
	  * Create a rectangle from two arbitrary points (figure out the width and height correctly)
	  * @param one
	  * @param two
	  * @return
	  */
	def apply(one:Point, two:Point):Rectangle = {
		val smallX = if (one.x < two.x) one.x else two.x
		val smallY = if (one.y < two.y) one.y else two.y
		val bigX = if (one.x > two.x) one.x else two.x
		val bigY = if (one.y > two.y) one.y else two.y
		this(Point(smallX, smallY), bigX - smallX, bigY - smallY)
	}
	val zero = Rectangle(Point.zero, 0, 0)
}
