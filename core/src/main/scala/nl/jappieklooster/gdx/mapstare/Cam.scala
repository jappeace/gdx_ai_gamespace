package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import nl.jappieklooster.gdx.mapstare.model._
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
	def move(direction: Vector2): Unit ={
		positionChange = Tile.fromVector(direction.add(positionChange))
		position += positionChange
		cam.translate(Tile.toPixels(direction))
	}
	def tileToScreenPixels(tile:Tile) :Vector2 = {
		Tile.toPixels(tile).sub(Tile.toPixels(position))
	}

	def mouseScreenPos():Vector3 = unproject(
		new Vector3(Gdx.input.getX, Gdx.input.getY,0)
	)
	def mouseScreenPos2():Vector2 = unproject(
		new Vector2(Gdx.input.getX, Gdx.input.getY)
	)

	def unproject(vector2: Vector2):Vector2 = {
		val v3 = unproject(new Vector3(vector2, 0))
		new Vector2(v3.x, v3.y)
	}
	private def unproject(vector: Vector3) = cam.unproject(
		vector.sub(
			new Vector3(
				Tile.toPixels(position).scl(1,-1),
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