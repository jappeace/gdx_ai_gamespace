package nl.jappieklooster.gdx.mapstare.controller

import nl.jappieklooster.gdx.mapstare.model.{IndividualController, GameTick, Individual}
import nl.jappieklooster.gdx.mapstare.model.math._

/**
  * It doesn't even do anything (useful for initialization)
  */
case object DoNothing extends IndividualController{
	def apply(gameTick: GameTick,ind: Individual) = ind
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
  * @param targetLocation a circle to give an error margin
  * @param ind
  * @return
  */
case class MoveTo(targetLocation: Circle) extends IndividualController{
	private var movement = Move.noMovement
	def apply(gameTick: GameTick, ind: Individual) = {
		if(movement.speed.lengthSq < Individual.maxSpeedSq){
			val distance = ind.position - targetLocation.position
			val scaled = distance * Individual.acceleration
			val change =
			// check if should do slowing down
				if(distance.lengthSq < movement.speed.lengthSq) -scaled else scaled
			movement = movement.copy(speed = movement.speed + change)
		}
		var result = movement(gameTick, ind)
		if(!targetLocation.contains(result.position)){
			movement = Move.noMovement
			result = result.copy(controller = DoNothing)
		}
		result
	}
}
