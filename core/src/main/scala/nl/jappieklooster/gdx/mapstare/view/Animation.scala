package nl.jappieklooster.gdx.mapstare.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite}
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.IntervalledUpdatable
import nl.jappieklooster.gdx.mapstare.model.math.Tile
import nl.jappieklooster.gdx.mapstare.model.{GameTick, Positionable, Individual, Entity}

class Animation(frametime:Float, sprites:Seq[Sprite], entity: Positionable, cam:Cam) extends IntervalledUpdatable with Renderable{
	setFrametime(frametime)
	var currentframe = 0
	var position:Vector2 = Vector2.Zero.cpy()
	val size = new Vector2(sprites.head.getWidth, sprites.head.getHeight)
	override def update(timeSinceLast:GameTick) = {
		position = cam.tileToScreenPixels(Tile.fromPixels(entity.position))
		super.update(timeSinceLast)
	}
	var change = 1
	override def intervalledUpdate(timeSinceLast:GameTick):Boolean = {
		if (currentframe >= (sprites.length-2)){
			change = -1
		}
		if(currentframe <= 0){
			change = 1
		}
		currentframe = currentframe + change
		true
	}
	def render(spriteBatch:SpriteBatch) = {
		val sprite = sprites(currentframe)
		sprite.setPosition(position.x, position.y)
		sprite.draw(spriteBatch)
	}
}

object Animation{
	def create(frametime:Float, frameCount:Int, frameWidth:Int, frameHeight:Int, texture:String)(entity: Positionable)(implicit cam:Cam) = {
		val spritesheet = new Texture(Gdx.files.internal(texture))
		new Animation(
			frametime,
			0.to(frameCount).map( x=> {
				val result = new Sprite(spritesheet, x*frameWidth, 0, frameWidth, frameHeight)
				result.setScale(0.1f,0.1f)
				result
			}
		),
			entity, cam
		)
	}
}