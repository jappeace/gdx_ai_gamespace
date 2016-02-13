package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math._

// builings trees etc
class Entity(var tile:Tile)
// swordman, archers, ents...
// called inidividual cause unit is occipied by scala
/**
 * We don't want this to be case classes because we want to modify it and have
 * these modifications be reflected all around the code base (ie in the views)
 * @param position
 * @param speed
 */
class Individual(var position:Vector2, var speed:Vector2 = Vector2.Zero)
object Individual{
	val maxSpeed = 2
	val maxSpeedSq = maxSpeed*maxSpeed
	val acceleration = 0.3f
}

