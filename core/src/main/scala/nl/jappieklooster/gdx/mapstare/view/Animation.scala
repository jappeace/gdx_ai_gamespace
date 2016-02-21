// This is a playground for AI, it offers a game space for AI to work with.
// Copyright (C) 2016 Jappe Klooster

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.If not, see <http://www.gnu.org/licenses/>.


package nl.jappieklooster.gdx.mapstare.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite}
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.IntervalledUpdatable
import nl.jappieklooster.gdx.mapstare.model.math.Tile
import nl.jappieklooster.gdx.mapstare.model.{GameTick, Positionable, Individual, Entity}

class Animation(frametime:Float, sprites:Seq[Sprite])(implicit cam:Cam) {
	if(frametime == 0){
		throw new Exception("division by zero imenent")
	}
	// FIXME: different unit types
	def render(individual: Individual, spriteBatch:SpriteBatch) = {
		val position = individual.position - cam.getPosition.topLeftPixels
		val currentFrame = ((individual.livingTime % ((sprites.length-1)*frametime))/frametime).toInt
		val sprite = sprites(currentFrame)
		sprite.setPosition(position.x, position.y)
		sprite.draw(spriteBatch)
		if(individual.selected){
			val offset = 10
			import Animation._
			line.setPosition(position.x,position.y + sprite.getHeight*0.56f)
			line.draw(spriteBatch)
			line.setPosition(position.x,position.y + sprite.getHeight*0.441f)
			line.draw(spriteBatch)
		}
	}
}

object Animation{
	val line = new Sprite(new Texture("line.png"),0,0,228,5)
	val scale = new Vector2(0.1f,0.1f)
	line.setScale(scale.x,scale.y)
	val lineAnimation = new Animation(1,Seq(line))
	def create(frametime:Float, frameCount:Int, frameWidth:Int, frameHeight:Int, texture:String)(implicit cam:Cam) = {
		val spritesheet = new Texture(Gdx.files.internal(texture))
		new Animation(
			frametime,
			0.to(frameCount).map( x=> {
				val result = new Sprite(spritesheet, x*frameWidth, 0, frameWidth, frameHeight)
				result.setScale(scale.x,scale.y)
				result
			})
		)
	}
}