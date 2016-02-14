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

class BuildState(world: World, cam: Cam, stage: Stage) extends State{
	lazy val batch = new SpriteBatch()
	lazy val font = new BitmapFont()
	lazy val swordmanFactory = Animation.create(0.2f, 4, 227, 320, "swordman.png") _
	lazy val camMoveController = new CamMovement()

	lazy val skin = new Skin(Gdx.files.internal("uiskin.json"))
	lazy val container = new Table(skin)
	var mouseAnimation:Option[Animation] = None
	var animations:Seq[Animation] = Nil
	lazy val selectionController = new SelectionBox(
		(one:Vector2, two:Vector2)=>updater.targets = updater.targets :+ Updateable.functionToUpdatable((float:GameTick)=>true) )
	val updater = new Updater()
	var x = 0
	def update(timeSinceLast:GameTick): Boolean={
		camMoveController.update(timeSinceLast)
		cam.cam.update()
		for(animation <- mouseAnimation){
			animation.update(timeSinceLast)
		}
		animations.foreach(_.update(timeSinceLast))
		true
	}
	override def enter(stateMachine: StateMachine):Unit = {
		font.setColor(Color.BLACK)
		val button = new TextButton("Click me", skin, "default")
		button.setWidth(200)
		button.setHeight(50)
		val dialog = new Dialog("click message", skin)
		dialog.addListener(OnClick(() => {
			dialog.hide()
		}
		))
		button.addListener(OnClick(() => {
			dialog.show(stage)
		}
		))
		implicit val plexer = new InputMultiplexer(camMoveController, stage, selectionController)
		Gdx.input.setInputProcessor(plexer)

		val scrolltable = new Table(skin)
		val scrollpane = new ScrollPane(scrolltable, skin, "default")
		val container = new Table(skin)
		container.add(scrollpane).width(200).height(100)
		container.row()
		container.add(button)
		val label = new TextButton("Swordman", skin, "default")
		label.addListener(
			new PlacementClick(
				factory = swordmanFactory,
				placeCallback = a=> animations = animations :+ a,
				followCallback = a=> mouseAnimation=a
			)
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
	}
}
