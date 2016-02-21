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

import scala.collection.mutable.ArrayBuffer

class World(from:WorldState = World.empty) {
	val entities = new ArrayBuffer[Entity](World.estmatedBuildingCount)
	val units = new ArrayBuffer[Individual](World.estmatedBuildingCount)

	entities ++= from.entities
	units ++= from.units

	def mapUnits(func:Individual => Individual) = {

		val newUnits = units.map(func)
		units.clear()
		units ++= newUnits
	}

	def copy() = WorldState(entities.toSeq, units.toSeq)
}
object World {
	// just give the buffers a bunch of space
	val estmatedBuildingCount = 50
	val estimatedUnitCount = estmatedBuildingCount * 10
	val empty = WorldState(Nil,Nil)
}

case class WorldState(entities: Seq[Entity], units:Seq[Individual])
