package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.{InputProcessor, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics.{OrthographicCamera, Color, GL20, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import nl.jappieklooster.gdx.mapstare.input.MouseTracker
import com.badlogic.gdx.math.Vector3

class Main extends ApplicationAdapter {
	lazy val batch:SpriteBatch = new SpriteBatch();
	lazy val img:Texture = new Texture("badlogic.jpg")

	lazy val font = new BitmapFont()
	val mousetracker = new MouseTracker()
	override def create() = {
		font.setColor(Color.BLACK)
		Gdx.input.setInputProcessor(mousetracker)
	}

	var x = 0.1f
	lazy val cam = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
	override def render() = {
		val pos = cam.unproject(new Vector3(Gdx.input.getX + Gdx.graphics.getWidth/2, Gdx.input.getY - Gdx.graphics.getHeight/2, 0))
		x += 0.1f
		Gdx.gl.glClearColor(1, 1, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		batch.begin();
		batch.draw(img, x, 0);
		font.draw(batch, s"aaah", x, 2*x)
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
		}
		batch.end();
	}
}
