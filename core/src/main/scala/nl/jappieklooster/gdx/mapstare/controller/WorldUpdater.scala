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


package nl.jappieklooster.gdx.mapstare.controller

import com.badlogic.gdx.math.Quaternion
import nl.jappieklooster.gdx.mapstare.model.{GameTick, World}

/**
 * Updates the world for things that always need to happen (ie movable things
 * should just move, regardless).
 *
 * @param world
 */
class WorldUpdater(world:World) extends Updateable {
	/**
	 *
	 * @param timeSinceLast
	 * @return should keep updating?
	 */
	override def update(tick: GameTick): Boolean = {
		// make all units ~~move~~ do shit.
		world.mapUnits(x=> {
			val result = x.controller(tick, x)
			// update time
			result.copy(livingTime = {
				var time = tick.timeSinceLastFrame + result.livingTime
				if(time < 0){ // overflow
					time = 0
				}
				time
			})
		})
		// always keep the updater active
		true
	}
}
