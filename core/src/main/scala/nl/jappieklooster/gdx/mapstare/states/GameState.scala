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
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.model.GameTick

/**
  * Instead of passing a whole collections of attributes arround we just
  * jammed everything in game for convienence, then we make it
  * globally accsable from this class. Architecture be dammed (although
  * this is probably a good compromise between convenience and making
  * everything static)
  *
  * Besides the gamestates aren't meant to do encapsulation. Instead they're
  * meant to group logic together and make transitions save.
 *
  * @param game
  */
abstract class GameState(game:Game) extends State {
	val world = game.world
	val stage = game.stage
	val cam = game.cam
	val inputMultiplexer = game.plexer
	/**
	  *
	  * @param tick
	  * @return should keep updating?
	  */
	override def update(tick: GameTick): Boolean = {
		true
	}
}
