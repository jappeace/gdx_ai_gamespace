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
