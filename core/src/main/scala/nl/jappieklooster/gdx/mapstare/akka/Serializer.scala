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


package nl.jappieklooster.gdx.mapstare.akka

import java.io._

import akka.util.ByteString

object Serializer {
	def serialize[T <: Serializable](obj: T): ByteString = {
		val byteOut = new ByteArrayOutputStream()
		val objOut = new ObjectOutputStream(byteOut)
		objOut.writeObject(obj)
		objOut.close()
		byteOut.close()
		ByteString(byteOut.toByteArray)
	}
	def deserialize[T <: Serializable](bytes: ByteString): T = {
		val byteIn = new ByteArrayInputStream(bytes.toArray)
		val objIn = new ObjectInputStream(byteIn)
		val obj = objIn.readObject().asInstanceOf[T]
		byteIn.close()
		objIn.close()
		obj
	}
}
