package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.controller.Updater
import nl.jappieklooster.gdx.mapstare.input.SelectionBox.SelectionCallback
import nl.jappieklooster.gdx.mapstare.model.{Individual, World}
import nl.jappieklooster.gdx.mapstare.view.Renderable
import nl.jappieklooster.gdx.mapstare.Cam
import org.slf4j.LoggerFactory
import nl.jappieklooster.gdx.mapstare.model.math._

object SelectionBox{
	def noCallback(one:Point, two:Point):Unit = {}
	type SelectionCallback = (Point, Point)=>Unit
	def toRectangle(one:Point, two:Point):Rectangle = {
		val smallX = if(one.x<two.x)one.x else two.x
		val smallY = if(one.y<two.y)one.y else two.y
		val bigX = if(one.x>two.x)one.x else two.x
		val bigY = if(one.y>two.y)one.y else two.y
		new Rectangle(smallX, smallY, bigX - smallX, bigY - smallY)
	}
	val log = LoggerFactory.getLogger(classOf[SelectionBox])
	def deselectUnits(world: World) = markUnitsAsSelected(world)(Point.zero,Point.zero)
	def markUnitsAsSelected(world:World)(one:Point, two:Point)(implicit cam: Cam):Unit= {
		val rectangle = toRectangle(cam.screenPointToWorld(one),cam.screenPointToWorld(two))
		log.info(s"rectangle $rectangle")
		world.units = world.units.map(unit =>
			unit.copy(selected = rectangle.contains(unit.position))
		)
	}
}
class SelectionBox(var callback:SelectionCallback= SelectionBox.noCallback _)(implicit cam:Cam) extends InputAdapter with Renderable{

	lazy val shapeRenderer = {
		val rend = new ShapeRenderer()
		rend.setColor(Color.WHITE)
		rend
	}
	var start:Option[Point] = None
	var end:Option[Point] = None
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		shapeRenderer.setProjectionMatrix(cam.cam.combined)
		start = Some(cam.unproject(Point(screenX, screenY)) + cam.getPosition.topLeftPixels)
		false
	}
	override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
		end = Some(cam.unproject(Point(screenX, screenY)) + cam.getPosition.topLeftPixels)
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

	override def render(spriteBatch: SpriteBatch): Unit = for(s <- start; e <- end) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
		shapeRenderer.rect(s.x, s.y, e.x -s.x, e.y-s.y)
		shapeRenderer.end()
	}
}
