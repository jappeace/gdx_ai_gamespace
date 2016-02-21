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
import com.badlogic.gdx.math.Vector2

object Direction extends Enumeration{
	type Direction = Value
	val Up, Down, Left, Right = Value

	val map = Map(Up -> Vector2.Y.cpy(),
		Down -> Vector2.Y.cpy().scl(-1),
		Left -> Vector2.X.cpy().scl(-1),
		Right -> Vector2.X.cpy()
	)
	implicit def toVectorID(direction: Direction):Vector2 = map(direction).cpy()

}
