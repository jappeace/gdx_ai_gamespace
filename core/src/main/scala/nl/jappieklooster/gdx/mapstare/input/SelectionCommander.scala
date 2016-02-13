package nl.jappieklooster.gdx.mapstare.input

import com.badlogic.gdx.math.Circle
import nl.jappieklooster.gdx.mapstare.controller.{MoveToController, Updater}
import nl.jappieklooster.gdx.mapstare.model.Individual
import nl.jappieklooster.gdx.mapstare.Cam

class SelectionCommander(updater:Updater, selected:Seq[Individual], camera: Cam) extends InputAdapter{
	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		val worldClickedWorldPos = camera.mouseScreenPos2()
		updater.targets ++= selected.map(x=>new MoveToController(x, new Circle(worldClickedWorldPos, 5)))
		true
	}
}
