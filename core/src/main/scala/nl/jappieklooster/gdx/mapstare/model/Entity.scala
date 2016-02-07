package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math._

// builings trees etc
case class Entity(position:Tile, health:Float)
// swordman, archers, ents...
case class Individual(position:Vector2, health:Float, speed:Vector2)

