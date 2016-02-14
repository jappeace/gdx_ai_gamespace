package nl.jappieklooster.gdx.mapstare.controller

import nl.jappieklooster.gdx.mapstare.model.{GameTick, Individual}
import nl.jappieklooster.gdx.mapstare.model.math._

trait IndividualController extends Individual.Controller{}
object Move extends IndividualController{
	def apply(gameTick: GameTick,ind: Individual) = ind.copy(location=ind.location+ind.speed * gameTick.timeSinceLastFrame)
}


/**
  *
  * @param targetLocation a circle to give an error margin
  * @param ind
  * @return
  */
class MoveTo(targetLocation: Circle) extends IndividualController{
	def apply(gameTick: GameTick, ind: Individual) = {
		var result = ind
		if(ind.speed.lengthSq < Individual.maxSpeedSq){
			val distance = ind.position - targetLocation.position
			val scaled = distance * Individual.acceleration
			val change =
			// check if should do slowing down
				if(distance.lengthSq < ind.speed.lengthSq) -scaled else scaled
			result = result.copy(speed = result.speed + change)
		}
		result = Move(gameTick, result)
		if(!targetLocation.contains(result.position)){
			result = result.copy(speed=Point.zero, controller = Move)
		}
		result
	}
}
