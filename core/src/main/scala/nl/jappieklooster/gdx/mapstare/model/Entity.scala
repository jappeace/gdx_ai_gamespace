package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.controller.{Move, IndividualController}
import nl.jappieklooster.gdx.mapstare.model.math._

sealed trait Positionable{
	def position:Point
}
// builings trees etc
case class Entity(tile:Tile) extends Positionable{
	override def position = tile.topLeftPixels
}
// swordman, archers, ents...
// called inidividual cause unit is occipied by scala
/**
 * We don't want this to be case classes because we want to modify it and have
 * these modifications be reflected all around the code base (ie in the views)
 *
 * @param position
 * @param speed (see it as a point on your speed indicator)
 * @param controller the function that updates thi individual
 */
case class Individual(
		location:Point,
		speed:Point = Point.zero,
		controller:Individual.Controller = Move,
		selected:Boolean = false
) extends Positionable{
	override def position = location
}
object Individual{
	type Controller = (GameTick, Individual) => Individual
	val maxSpeed = 2
	val maxSpeedSq = maxSpeed*maxSpeed
	val acceleration = 0.3f
}

