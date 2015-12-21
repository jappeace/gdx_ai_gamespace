package nl.jappieklooster.gdx.mapstare

import com.badlogic.gdx.{InputProcessor, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.math.Vector3

class Main extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()
	lazy val img = new Texture("badlogic.jpg")

	lazy val text = new Pixmap(40,40, Pixmap.Format.RGBA8888)
	lazy val custom = new Texture(text)
	lazy val font = new BitmapFont()
	override def create() = {
		font.setColor(Color.BLACK)

		text.setColor(Color.rgba8888(Color.RED))
		text.drawLine(0,4,40,40)
		text.drawLine(3,5,40,40)
		text.drawLine(4,10,40,40)
	}

	var x = 0.1f
	lazy val cam = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
	var positions = List[(Texture,Float,Float)]()
	override def render() = {
		val pos = cam.unproject(new Vector3(Gdx.input.getX + Gdx.graphics.getWidth/2, Gdx.input.getY - Gdx.graphics.getHeight/2, 0))
		x += 0.1f
		Gdx.gl.glClearColor(1, 1, 0, 1)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		batch.begin()
		batch.draw(img, x, 0)
		font.draw(batch, s"aaah", x, 2*x)
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
			positions = (custom, pos.x,pos.y) :: positions
		}
		if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
			positions = Nil
		}
		for(pos <- positions){
			batch.draw(pos._1, pos._2, pos._3)
		}
		batch.end()
	}
}
