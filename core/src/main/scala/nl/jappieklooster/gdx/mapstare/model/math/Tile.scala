package nl.jappieklooster.gdx.mapstare.model.math

import com.badlogic.gdx.math._

/**
  * A tile in the world, buildings can be alligned to this for example
  * @param left
  * @param top
  */
case class Tile(left:Int, top:Int){
	lazy val topLeftPixels = Tile.toPixels(Tile.toPoint(this))
	lazy val bottomRightPixels = Tile.toPixels(Tile.toPoint(this)+Point.id)

	def contains(pixels: Vector2): Boolean = {
		val tl = topLeftPixels
		if (tl.x > pixels.x) {
			return false
		}
		if (tl.y > pixels.y) {
			return false
		}
		val br = bottomRightPixels
		if (br.x <= pixels.x) {
			return false
		}
		if (br.y <= pixels.y) {
			return false
		}
		true
	}
	def -(rhs:Tile):Tile = Tile(left-rhs.left, top-rhs.top)
	def +(rhs:Tile):Tile = Tile(left+rhs.left, top+rhs.top)
	lazy val neg = Tile(-left,-top)
}
object Tile{
	val width = 32
	val height = 32
	val zero = Tile(0,0)

	def fromVector(vector2: Point):Tile = Tile(vector2.x.toInt, vector2.y.toInt)
	def fromPixels(vector: Vector3):Tile = Tile((vector.x/width).toInt, (vector.y/height).toInt)
	def fromPixels(point: Point):Tile = Tile((point.x/width).toInt, (point.y/height).toInt)
	def toPoint(tile: Tile):Point = new Point(tile.left, tile.top)
	def toPixels(point: Point):Point = point.scale(Tile.width, Tile.height)
}