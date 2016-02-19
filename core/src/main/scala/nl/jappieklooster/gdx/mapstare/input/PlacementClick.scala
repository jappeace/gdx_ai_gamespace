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
	val processor = new InputAdapter {
			override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
				button match{
					case Buttons.LEFT =>
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
					case Buttons.RIGHT =>
						plexer.removeProcessor(this)
						true
					case _ => false
				}
			}
		}

	def screenToTile(screen:Vector3) = Tile.fromPixels(screen) + cam.getPosition - Tile(3,4)
}
