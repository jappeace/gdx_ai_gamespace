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


package nl.jappieklooster.gdx.mapstare.states

import com.badlogic.gdx.{InputProcessor, InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{MoveTo, Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input._
import nl.jappieklooster.gdx.mapstare.model.{Individual, World, GameTick}
import nl.jappieklooster.gdx.mapstare.model.math._
import nl.jappieklooster.gdx.mapstare.view.{Renderable, Animation}
import org.slf4j.LoggerFactory
import nl.jappieklooster.gdx.mapstare.Game

class FightState (game:Game) extends GameState(game){
	val log = LoggerFactory.getLogger(classOf[FightState])
	val clickHandler = new MouseClickAdapter {
		override def leftClick(screenX:Int, screenY:Int, pointer:Int) = {
			true
		}
		override def rightClick(screenX:Int, screenY:Int, pointer:Int):Boolean = {
			// TODO: update units
			world.units.map(x=> if(x.selected){
				x.copy(
					controller = MoveTo(cam.screenToTile(cam.unproject(Point(screenX,screenY))).topLeftPixels)
				)
			}else x)
			true
		}
	}
	override def enter(gameState: StateMachine): Unit ={
		game.customRenders = game.customRenders :+ new Renderable {
			var x = 0D
			override def render(spriteBatch: SpriteBatch): Unit = {
				x += 0.02D
				Animation.line.setPosition(200 ,300+ (Math.sin(x) *50).toInt)
				Animation.line.draw(spriteBatch)
			}
		}
		game.selectionController.callback = SelectionBox.markUnitsAsSelected(game.world)
		inputMultiplexer.addProcessor(clickHandler)
	}
	override def exit():Unit = {
		inputMultiplexer.removeProcessor(clickHandler)
	}
}
