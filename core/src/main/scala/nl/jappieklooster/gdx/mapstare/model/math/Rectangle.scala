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
