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
		selected:Boolean = false,
		livingTime:Float = 0 // in seconds, used for animations for example
) extends Positionable{
	override def position = location
}

object Individual{
	val maxSpeed = 50
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
