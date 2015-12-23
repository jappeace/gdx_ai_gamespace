package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math.Vector2

case class Tile(left:Int, top:Int){
	lazy val topLeftPixels = new Vector2(left * Tile.width, top * Tile.height)
	lazy val bottomRightPixels = new Vector2((left + 1) * Tile.width, (top + 1) * Tile.height)

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


}
object Tile{
	val width = 32
	val height = 32
	val zero = Tile(0,0)

	def fromVector(vector2: Vector2):Tile = Tile(vector2.x.toInt, vector2.y.toInt)
	implicit def toVector(tile: Tile):Vector2 = new Vector2(tile.left, tile.top)
}