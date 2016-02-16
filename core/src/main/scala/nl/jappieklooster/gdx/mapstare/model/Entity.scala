package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math._
import nl.jappieklooster.gdx.mapstare.model.math._

trait Positionable{
	def position:Point
}
// builings trees etc
case class Entity(tile:Tile) extends Positionable{
	override def position = tile.topLeftPixels
}


