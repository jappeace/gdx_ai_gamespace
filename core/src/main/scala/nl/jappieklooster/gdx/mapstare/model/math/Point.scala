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
import com.badlogic.gdx.math.Vector2

/**
  * Immutable point in a plane
  * @param x
  * @param y
  */
case class Point(x:Float, y:Float) {
	def +(v: Point) = Point(x + v.x, y + v.y)
	def -(v: Point) = Point(x - v.x, y - v.y)
	def *(v: Point) = Point(x * v.x, y * v.y)
	def /(v: Point) = Point(x / v.x, y / v.y)
	def unary_- = negate

	def scale(to:Float) = transform(x => x*to)
	def scale(one:Float, two:Float) = Point(x*one, y*two)

	/**
	  * apply the same operation to all the elements of the vector and create
	  * a new vector with that
	  * @param wit the function to apply
	  * @return
	  */
	def transform(wit : (Float) => Float) = Point(wit(x), wit(y))

	/**
	  * return either yourself if already absolute or a new absoulte
	  * Vector, keep the ref (because immutable)
	  */
	lazy val abs = if((Math.abs(x) == x) && (Math.abs(y) == y)){
		this
	}else{
		transform(Math.abs)
	}

	lazy val negate = Point(-x, -y)

	lazy val lengthSq = x*x + y*y
	lazy val length = Math.sqrt(lengthSq).toFloat
	lazy val normal = transform(x => x * (1/length))
}
object Point{
	val zero = Point(0,0)
	val id = Point(1,1)
	val idX = Point(1,0)
	val idY = Point(0,1)

	/*
	 *  implicit conversion from and to JME3 mutable vectors.
	 *  Its probably slow, but micro optimastions can be inserted for the
	 *  imutable ones rather easily.
	 */
	implicit def toMutable(v:Point):Vector2 = new Vector2(v.x,v.y)
	implicit def fromMutable(v:Vector2):Point = Point(v.x,v.y)

	/**
	  * allows doing cool stuf such as Point(3,3,3)/3 without defining
	  * more methods.
	  * @param v
	  * @return
	  */
	implicit def fromFloat(v:Float):Point = Point(v,v)
	implicit def fromInt(v:Int):Point = Point(v,v)
}
