package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Dialog, TextButton, Skin}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{InputMultiplexer, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import nl.jappieklooster.gdx.mapstare.input.CamController

class Main() extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()

	lazy val font = new BitmapFont()
	lazy val maprendeer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("map.tmx"))
	lazy val cam = Cam.cam
	lazy val stage = new Stage(new ScreenViewport())
	lazy val skin = new Skin(Gdx.files.internal("uiskin.json"))
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
		stage.addActor(button)
		Gdx.input.setInputProcessor(new InputMultiplexer(new CamController(), stage))
	}

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
		batch.end()
		stage.act(Gdx.graphics.getDeltaTime)
		stage.draw()
	}
}
