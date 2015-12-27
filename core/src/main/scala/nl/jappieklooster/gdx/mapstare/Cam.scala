package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import nl.jappieklooster.gdx.mapstare.model._
import Direction._
import com.badlogic.gdx.math._

/**
 * Tha camera
 */
class Cam {
	val cam = new OrthographicCamera()
	private var position = Tile.zero
	private var positionChange = Tile.zero
	toOrtho(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
	def move(direction: Vector2): Unit ={
		positionChange = Tile.fromVector(direction.add(positionChange))
		position += positionChange
		cam.translate(Tile.toPixels(direction))
	}
	def tileToScreenPixels(tile:Tile) :Vector2 = Tile.toPixels(tile).sub(cam.position.x, cam.position.y)

	def mouseScreenPos():Vector3 = {
		cam.unproject(new Vector3(Gdx.input.getX, Gdx.input.getY,0).sub(
			new Vector3(Tile.toPixels(position).scl(1,-1), 0)))
	}

	def toOrtho(width:Float, height:Float) = {
		cam.setToOrtho(false, width, height)
		cam.update()
		position = Tile.zero
	}

}

object Cam {
	implicit val cam = new Cam()
}