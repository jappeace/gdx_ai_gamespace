package nl.jappieklooster.gdx.mapstare.model

import nl.jappieklooster.gdx.mapstare.model.math._
import nl.jappieklooster.gdx.mapstare.controller.{DoNothing, Move}

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
		controller:IndividualController = DoNothing,
		selected:Boolean = true,
		livingTime:Float = 0 // in seconds, used for animations for example
) extends Positionable{
	override def position = location
}

object Individual{
	val maxSpeed = 2
	val maxSpeedSq = maxSpeed*maxSpeed
	val acceleration = 0.3f
}
/** Individuals can have behavior. We allow this to be part of the datastructure
  * This may seem like breaking of the classical MVC paradigm, but if you think
  * about it its not. Because isn't the behaviour also part of the model?
  *
  * Anyways this allows the views to question for example what an individual
  * is doing. It also allows an immutable individual. So the overall architecture
  * becomes a lot less indirect. The logical parts are still sealed away in
  * their own functions but they also have access to the individual information.
  *
  * In its current form we only allow one Controller to be attached. I think
  * more isn't necessary (because you can make controllers arbitrarily complex).
  *
  * The controllers can also receive more information on construction. (such
  * as location of other NPC's.) I tried to keep the function prototype as small
  * as possible.
 */
trait IndividualController extends ((GameTick, Individual) => Individual){}
