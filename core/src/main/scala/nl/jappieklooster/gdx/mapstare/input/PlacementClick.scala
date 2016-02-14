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


class PlacementClick(factory:(Individual)=>Animation, placeCallback:(Animation)=>Unit, followCallback:(Option[Animation])=>Unit)(implicit cam:Cam, plexer:InputMultiplexer) extends ClickListener{
	override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
		click()
	}

	private def click() = {
		def screenToTile(screen:Vector3) =
			Tile.fromPixels(screen) + cam.getPosition - Tile(3,4)
		plexer.addProcessor(new InputAdapter {
			override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
				button match{
					case Buttons.LEFT =>
						placeCallback(factory(
							Individual(
								screenToTile(
									new Vector3(
										screenX,
										Gdx.graphics.getHeight - screenY,
										0
									)
								).topLeftPixels
							)
						))
						true
					case Buttons.RIGHT =>
						followCallback(None)
						plexer.removeProcessor(this)
						true
					case _ => false
				}
			}
		})
		followCallback(Some(
			factory(
				Individual(
					Point(
						cam.mouseScreenPos().x,
						cam.mouseScreenPos().z
					)
				)
			)
		))
	}
}
