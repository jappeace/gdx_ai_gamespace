package nl.jappieklooster.gdx.mapstare.controller

import com.badlogic.gdx.math.Quaternion
import nl.jappieklooster.gdx.mapstare.model.World

/**
 * Updates the world for things that always need to happen (ie movable things
 * should just move, regardless).
 * @param world
 */
class WorldUpdater(world:World) extends Updateable {
	/**
	 *
	 * @param timeSinceLast
	 * @return should keep updating?
	 */
	override def update(timeSinceLast: Float): Boolean = {
		// make all units move
		world.units.foreach(unit=>unit.position.add(unit.speed.cpy().scl(timeSinceLast)))
		true
	}
}
