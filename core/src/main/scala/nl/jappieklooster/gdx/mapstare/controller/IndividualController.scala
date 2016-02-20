package nl.jappieklooster.gdx.mapstare.controller

import nl.jappieklooster.gdx.mapstare.model.{IndividualController, GameTick, Individual}
import nl.jappieklooster.gdx.mapstare.model.math._
import org.slf4j.LoggerFactory

/**
  * It doesn't even do anything (useful for initialization)
  */
case object DoNothing extends IndividualController{
	def apply(gameTick: GameTick,ind: Individual) = {
		ind
	}
}
/**
  * The move controller lets the individual move according to its speed.
  */
case class Move(speed:Point) extends IndividualController{
	def apply(gameTick: GameTick,ind: Individual):Individual = {
		if(speed == Point.zero){
			return DoNothing(gameTick, ind)
		}
		ind.copy(location=ind.location+speed * gameTick.timeSinceLastFrame)
	}
}
object Move{
	val noMovement = Move(Point.zero)
}


/**
  * Move to an (error margined) location in gamespace, with acceleartion
  * and deceleration.
 *
  * @param targetLocation a circle to give an error margin
  * @param ind
  * @return
  */
case class MoveTo(targetLocation: Circle) extends IndividualController{
	private var movement = Move.noMovement
	private val log = LoggerFactory.getLogger(classOf[MoveTo])
	log.info(s"moving to $targetLocation")
	def apply(gameTick: GameTick, ind: Individual) = {
		val distance = targetLocation.position - ind.position
		import Individual._
		if(movement.speed.lengthSq / acceleration < maxSpeedSq || distance.normal != movement.speed.normal){
			val scaled = distance.normal * acceleration

			val accSq = acceleration * acceleration
			// check if should do slowing down
			val change =
				if(distance.lengthSq * accSq < movement.speed.lengthSq) -scaled else scaled
			movement = movement.copy(speed = movement.speed + change)
		}
		var result = movement(gameTick, ind)
		if(targetLocation.contains(result.position)){
			result = result.copy(controller = DoNothing)
		}
		result
	}
}
object MoveTo{
	/**
	  * cause getting to points can be a little messy,
	  * also floating point rounding bullshit
	  */
	val defaultErrorRadius = 5f
	def apply(point: Point):MoveTo = MoveTo(Circle(point,defaultErrorRadius))
}
