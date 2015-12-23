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
	cam.setToOrtho(false, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
	private var position = Tile.zero
	def move(direction: Direction): Unit ={
		position = Tile.fromVector(direction.add(position))
		cam.translate(direction.scl(Tile.width, Tile.height))
	}

	def mouseScreenPos():Vector3 = cam.unproject(new Vector3(Gdx.input.getX, Gdx.input.getY,0)).sub(
		new Vector3(position.scl(Tile.width, Tile.height), 0))
}

object Cam {
	implicit val cam = new Cam()
}