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
import nl.jappieklooster.gdx.mapstare.model.math._
import nl.jappieklooster.gdx.mapstare.view.Animation
import nl.jappieklooster.gdx.mapstare.Game

class BuildState(game:Game) extends GameState(game){

	// TODO: make this scalable (also multiple types of units, snould acutally be doable with a list)
	val clickThing = new PlacementClick(
			placeCallback = a=> world.units +=  a,
			cam,
			inputMultiplexer
		)
	override def enter(stateMachine: StateMachine):Unit = {
		val builder = new UIFactory()
		val button = builder.button("Start!")
		button.setWidth(200)
		button.setHeight(50)
		val dialog = builder.dialog("click message")
		dialog.addListener(OnClick(() => {
			dialog.hide()
		}
		))
		button.addListener(OnClick(() => {
			if(world.units == Nil){
				dialog.getTitleLabel.setText("No units made :s")
				dialog.show(stage)
			}
			stateMachine.changeTo(new FightState(game))
		}
		))

		val (scrolltable, scrollpane) = builder.scrollPane()
		val container = builder.table()
		container.add(scrollpane).width(200).height(100)
		container.row()
		container.add(button)
		val label = builder.button("Start!")
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
