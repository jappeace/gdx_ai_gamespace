package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.controller.Updater
import nl.jappieklooster.gdx.mapstare.input.SelectionBox.SelectionCallback
import nl.jappieklooster.gdx.mapstare.model.{World, Tile}
import nl.jappieklooster.gdx.mapstare.view.Renderable
import nl.jappieklooster.gdx.mapstare.Cam

object SelectionBox{
	type SelectionCallback = (Vector2, Vector2)=>Unit
	def markUnitsAsSelected(updater:Updater,world:World)(one:Vector2, two:Vector2):Unit = {
		// TODO: filter units based on selection coordinates, Maybe using Rectangle??
		// val units = world.units.filter(_.position.)
	}
}
class SelectionBox(callback:SelectionCallback)(implicit cam:Cam) extends InputAdapter with Renderable{

	lazy val shapeRenderer = new ShapeRenderer()
	var start:Option[Vector2] = None
	var end:Option[Vector2] = None
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		shapeRenderer.setProjectionMatrix(cam.cam.combined)
		start = Some(cam.unproject(new Vector2(screenX, screenY)).add(cam.getPosition.pixels))
		false
	}
	override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
		end = Some(cam.unproject(new Vector2(screenX, screenY)).add(cam.getPosition.pixels))
		true
	}
	override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		for(s <- start; e <- end){
			callback(s,e)
		}
		start = None
		end = None
		false
	}

	override def render(spriteBatch: SpriteBatch): Unit = {
		shapeRenderer.setColor(Color.WHITE)
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
		for(s <- start; e <- end) yield {
			println(s"draw start: $s end: $e")

			shapeRenderer.rect(s.x, s.y, e.x -s.x, e.y-s.y)
		}
		shapeRenderer.end()
	}
}
