package nl.jappieklooster.gdx.mapstare.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite}
import nl.jappieklooster.gdx.mapstare.IntervalledUpdatable

class Animation(frametime:Float, sprites:Seq[Sprite]) extends IntervalledUpdatable{
	setFrametime(frametime)
	var currentframe = 0
	val position:Vector2 = Vector2.Zero.cpy()
	override def intervalledUpdate(timeSinceLast:Float) = {
		currentframe = (currentframe + 1) % (sprites.length-1)
	}
	def render(spriteBatch:SpriteBatch) = {
		val sprite = sprites(currentframe)
		sprite.setPosition(position.x, position.y)
		sprite.draw(spriteBatch)
	}
}

object Animation{
	def create(frametime:Float, frameCount:Int, frameWidth:Int, frameHeight:Int, texture:String) = {
		val spritesheet = new Texture(Gdx.files.internal(texture))
		new Animation(
			frametime,
			0.to(frameCount).map( x=> {
				val result = new Sprite(spritesheet, x*frameWidth, 0, frameWidth, frameHeight)
				result.setScale(0.1f,0.1f)
				result
			}
		)
		)
	}
}