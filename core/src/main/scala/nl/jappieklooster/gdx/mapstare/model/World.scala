package nl.jappieklooster.gdx.mapstare.model

import scala.collection.mutable.ArrayBuffer

class World() {
	val entities = new ArrayBuffer[Entity](World.estmatedBuildingCount)
	val units = new ArrayBuffer[Individual](World.estmatedBuildingCount)
	def mapUnits(func:Individual => Individual) = {
		val newUnits = units.map(func)
		units.clear()
		units.insertAll(0, newUnits)
	}
}
object World {
	// just give the buffers a bunch of space
	val estmatedBuildingCount = 50
	val estimatedUnitCount = estmatedBuildingCount * 10
}

