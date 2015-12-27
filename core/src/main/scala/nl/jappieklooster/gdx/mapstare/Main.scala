package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{InputMultiplexer, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import nl.jappieklooster.gdx.mapstare.input.CamController
import nl.jappieklooster.gdx.mapstare.model._
import nl.jappieklooster.gdx.mapstare.view.Animation


class Main() extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()

	lazy val font = new BitmapFont()
	lazy val maprendeer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("map.tmx"))
	lazy val cam = Cam.cam
	lazy val stage = new Stage(new ScreenViewport(), batch)
	lazy val swordman = Animation.create(0.2f, 4, 227, 320, "swordman.png", Swordman(Tile(10,10)))
	lazy val controller = new CamController()

	lazy val skin = new Skin(Gdx.files.internal("uiskin.json"))
	lazy val container = new Table(skin)
	override def create() = {
		font.setColor(Color.BLACK)
		val button = new TextButton("Click me", skin, "default")
		button.setWidth(200)
		button.setHeight(50)
		val dialog = new Dialog("click message", skin)
		dialog.addListener(new ClickListener(){
			override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
				dialog.hide()
			}
		})
		button.addListener(new ClickListener(){
			override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
				println("clicked??")
				dialog.show(stage)
			}
		})
		Gdx.input.setInputProcessor(new InputMultiplexer(controller, stage))

		val scrolltable = new Table(skin)
		val scrollpane = new ScrollPane(scrolltable, skin, "default")
		val container = new Table(skin)
		container.add(scrollpane).width(200).height(100)
		container.row()
		container.add(button)
		val label = new TextButton("blah", skin, "default")
		label.addListener(new ClickListener(){
			override def clicked(event:InputEvent, x:Float, y:Float):Unit = {
				println("clicke blah")
			}
		})
		scrolltable.add(label)
		scrolltable.row()
		scrolltable.add("bleh")
		scrolltable.row()
		scrolltable.add("bleh")
		scrolltable.row()
		scrolltable.add("bleh")
		scrolltable.row()
		scrolltable.add("bleh")
		scrolltable.row()
		scrolltable.add("bleh")
		scrolltable.row()
		scrolltable.add("bleh")
		container.setWidth(200)
		container.setHeight(120)
		stage.addActor(container)
	}

	var x = 0
	override def render() = {
		val pos = cam.mouseScreenPos()
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		cam.cam.update()
		maprendeer.setView(cam.cam)
		maprendeer.render()
		batch.begin()
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
		}
		swordman.update(Gdx.graphics.getDeltaTime)
		swordman.render(batch)
		controller.update(Gdx.graphics.getDeltaTime)
		batch.end()
		stage.act(Gdx.graphics.getDeltaTime)
		stage.draw()
	}
	override def resize (width:Int, height:Int):Unit = {
		println("resize")
		cam.toOrtho(width, height)
		stage.getViewport.update(width, height, true)
	}
}
