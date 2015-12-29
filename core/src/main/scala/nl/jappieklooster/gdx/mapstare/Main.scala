package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{InputMultiplexer, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import nl.jappieklooster.gdx.mapstare.input._
import nl.jappieklooster.gdx.mapstare.model._
import nl.jappieklooster.gdx.mapstare.view.Animation

import com.badlogic.gdx.math._

class Main() extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()

	lazy val font = new BitmapFont()
	lazy val maprendeer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("map.tmx"))
	lazy val cam = Cam.cam
	lazy val stage = new Stage(new ScreenViewport(), batch)
	lazy val swordmanFactory = Animation.create(0.2f, 4, 227, 320, "swordman.png") _
	lazy val controller = new CamController()

	lazy val skin = new Skin(Gdx.files.internal("uiskin.json"))
	lazy val container = new Table(skin)
	var mouseAnimation:Option[Animation] = None
	var animations:Seq[Animation] = Nil
	lazy val selectionController = new SelectionController
	override def create() = {
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
		val plexer = new InputMultiplexer(controller, stage, selectionController)
		Gdx.input.setInputProcessor(plexer)

		val scrolltable = new Table(skin)
		val scrollpane = new ScrollPane(scrolltable, skin, "default")
		val container = new Table(skin)
		container.add(scrollpane).width(200).height(100)
		container.row()
		container.add(button)
		val label = new TextButton("Swordman", skin, "default")
		label.addListener(
			new PlacementClick(plexer,
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

	var x = 0
	def update(timeSinceLast:Float): Unit ={
		controller.update(timeSinceLast)
		cam.cam.update()
		for(animation <- mouseAnimation){
			animation.update(timeSinceLast)
		}
		animations.foreach(_.update(timeSinceLast))
	}
	override def render() = {
		update(Gdx.graphics.getDeltaTime)

		val pos = cam.mouseScreenPos()
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		maprendeer.setView(cam.cam)
		maprendeer.render()
		batch.begin()
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
		}
		for(animation <- mouseAnimation){
			animation.render(batch)
		}
		animations.foreach(_.render(batch))
		batch.end()
		selectionController.render(batch)
		stage.act(Gdx.graphics.getDeltaTime)
		stage.draw()
	}
	override def resize (width:Int, height:Int):Unit = {
		println("resize")
		cam.toOrtho(width, height)
		stage.getViewport.update(width, height, true)
	}
}
