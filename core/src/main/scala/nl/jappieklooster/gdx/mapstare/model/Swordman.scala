package nl.jappieklooster.gdx.mapstare.model

import com.badlogic.gdx.math.Vector2

trait Entity{
	def getPosition:Tile
}
case class Swordman(position:Tile) extends Entity{
	override def getPosition = position
}
