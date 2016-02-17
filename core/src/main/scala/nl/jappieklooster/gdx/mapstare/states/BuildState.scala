package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.{InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.math.Vector2
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input.gui.OnClick
import nl.jappieklooster.gdx.mapstare.input.{PlacementClick, SelectionBox, CamMovement}
import nl.jappieklooster.gdx.mapstare.model.{World, GameTick}
import nl.jappieklooster.gdx.mapstare.view.Animation

class BuildState(world: World, stage: Stage)(implicit cam: Cam, inputMultiplexer: InputMultiplexer) extends State{

	lazy val skin = new Skin(Gdx.files.internal("uiskin.json"))
	var animations:Seq[Animation] = Nil
	lazy val selectionController = new SelectionBox(
		(one:Vector2, two:Vector2)=>updater.targets = updater.targets :+ Updateable.functionToUpdatable((float:GameTick)=>true) )
	val updater = new Updater()
	var x = 0
	def update(timeSinceLast:GameTick): Boolean={
		cam.cam.update()
		selectionController.render(null)
		true
	}
	val clickThing = new PlacementClick(
			placeCallback = a=> world.units = world.units :+ a
		)
	override def enter(stateMachine: StateMachine):Unit = {
		val button = new TextButton("Start!", skin, "default")
		button.setWidth(200)
		button.setHeight(50)
		val dialog = new Dialog("click message", skin)
		dialog.addListener(OnClick(() => {
			dialog.hide()
		}
		))
		button.addListener(OnClick(() => {
			if(world.units == Nil){
				dialog.getTitleLabel.setText("No units made :s")
				dialog.show(stage)
			}
			stateMachine.changeTo(new FightState(world, stage))
		}
		))

		val scrolltable = new Table(skin)
		val scrollpane = new ScrollPane(scrolltable, skin, "default")
		val container = new Table(skin)
		container.add(scrollpane).width(200).height(100)
		container.row()
		container.add(button)
		val label = new TextButton("Swordman", skin, "default")
		label.addListener(
			clickThing
		)
		scrolltable.add(label)
		scrolltable.row()
		scrolltable.add("Horseman")
		scrolltable.row()
		scrolltable.add("Archer")
		scrolltable.row()
		scrolltable.add("Catapult")
		scrolltable.row()
		scrolltable.add("Wardog")
		scrolltable.row()
		scrolltable.add("Elephant")
		scrolltable.row()
		scrolltable.add("Dragon")
		container.setWidth(200)
		container.setHeight(120)
		stage.addActor(container)

	}
	override def exit():Unit = {
		stage.getRoot.clear()
		inputMultiplexer.removeProcessor(clickThing.processor)
	}
}
