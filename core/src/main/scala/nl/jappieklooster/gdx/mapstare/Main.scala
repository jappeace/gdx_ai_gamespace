package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.{InputProcessor, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.math.Vector3
import nl.jappieklooster.gdx.mapstare.input.CamController

class Main() extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()

	lazy val font = new BitmapFont()
	lazy val maprendeer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("map.tmx"))
	lazy val cam = Cam.cam
	override def create() = {
		font.setColor(Color.BLACK)
		Gdx.input.setInputProcessor(new CamController())
	}

	var positions = List[(Texture,Float,Float)]()
	override def render() = {
		val pos = cam.mouseScreenPos()
		Gdx.gl.glClearColor(1, 1, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		cam.cam.update()
		maprendeer.setView(cam.cam)
		maprendeer.render()
		batch.begin()
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
		}
		for(pos <- positions){
			batch.draw(pos._1, pos._2, pos._3)
		}
		batch.end()
	}
}
