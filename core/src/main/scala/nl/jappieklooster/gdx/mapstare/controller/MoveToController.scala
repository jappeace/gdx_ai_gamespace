package nl.jappieklooster.gdx.mapstare.controller

import nl.jappieklooster.gdx.mapstare.model.Individual

import com.badlogic.gdx.math._
class MoveToController(unit:Individual, targetLocation:Circle) extends Updateable{
	/**
	 *
	 * @param timeSinceLast
	 * @return should keep updating?
	 */
	override def update(timeSinceLast: Float): Boolean = {
		val done = !targetLocation.contains(unit.position)
		if(unit.speed.len2 < Individual.maxSpeedSq){
			val distance = unit.position.cpy().sub(targetLocation.x, targetLocation.y)
			val scaled = distance.scl(Individual.acceleration)
			val change = if(distance.len2() < unit.speed.len2) scaled.scl(-1) else scaled
			unit.speed.add(change)
		}
		if(done){
			unit.speed = Vector2.Zero
		}
		done
	}
}
