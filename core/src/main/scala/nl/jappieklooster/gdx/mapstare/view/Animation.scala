package nl.jappieklooster.gdx.mapstare.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite}

class Animation(frametime:Float, sprites:Seq[Sprite]) {
	var counter = 0f
	var currentframe = 0
	def render(spriteBatch:SpriteBatch, timeSinceLast:Float) = {
		counter += timeSinceLast
		if(counter > frametime){
			counter -= frametime
			currentframe = (currentframe + 1) % (sprites.length-1)
		}
		sprites(currentframe).draw(spriteBatch)
	}
}

object Animation{
	def create(frametime:Float, frameCount:Int, frameWidth:Int, frameHeight:Int, texture:String) = {
		val spritesheet = new Texture(Gdx.files.internal(texture))
		new Animation(frametime,
		0.to(frameCount).map( x=> {
			val width = 227
			val height = 320
			val result = new Sprite(spritesheet, x*frameWidth, 0, frameWidth, frameHeight)
			result.setPosition(200,120)
			result.setScale(0.4f,0.4f)
			result
		}
		)
		)
	}
}