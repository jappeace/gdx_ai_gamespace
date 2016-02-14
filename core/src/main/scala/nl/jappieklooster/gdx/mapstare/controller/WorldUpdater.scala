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
		world.units = world.units.map(x=>x.controller(tick, x))
		// always keep the updater active
		true
	}
}
