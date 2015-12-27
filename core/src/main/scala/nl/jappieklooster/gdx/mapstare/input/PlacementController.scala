package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import nl.jappieklooster.gdx.mapstare.model.{Entity, Swordman, Tile}
import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.view.Animation
import nl.jappieklooster.gdx.mapstare.Cam


class PlacementController(plexer:InputMultiplexer, factory:(Entity)=>Animation, placeCallback:(Animation)=>Unit, followCallback:(Option[Animation])=>Unit)(implicit cam:Cam) extends ClickListener{
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
							Swordman(
								screenToTile(
									new Vector3(
										screenX,
										Gdx.graphics.getHeight - screenY,
										0
									)
								)
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
			factory(new Entity {
				override def getPosition:Tile = {
					screenToTile(cam.mouseScreenPos())
				}
			}
			)
		))
	}
}
