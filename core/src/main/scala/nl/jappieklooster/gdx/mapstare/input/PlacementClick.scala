package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import nl.jappieklooster.gdx.mapstare.model.math.Tile
import nl.jappieklooster.gdx.mapstare.model.{Individual, Entity}
import nl.jappieklooster.gdx.mapstare.model.math.Point
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.view.Animation
import nl.jappieklooster.gdx.mapstare.Cam


class PlacementClick(placeCallback:(Individual)=>Unit, cam:Cam, plexer:InputMultiplexer) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		plexer.addProcessor(processor)
	}
	val processor = new MouseClickAdapter {
		override def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			placeCallback(
				Individual(
					screenToTile(
						new Vector3(
							screenX,
							Gdx.graphics.getHeight - screenY,
							0
						)
					).topLeftPixels
				)
			)
			true
		}
		override def leftClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			plexer.removeProcessor(this)
			true
		}
		}

	def screenToTile(screen:Vector3) = Tile.fromPixels(screen) + cam.getPosition - Tile(3,4)
}
