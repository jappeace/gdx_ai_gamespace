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


package nl.jappieklooster.gdx.test

import org.scalatest.{Matchers, FlatSpec}
import scala.pickling.Defaults._
import scala.pickling.binary._

class PicklerSpec extends FlatSpec with Matchers{
	case class Figure(bones:Seq[OvalShape])
	case class OvalShape(length:Int, width:Float)
	val circle = OvalShape(40,103f)
	val human = Figure(Seq(circle, circle))
	"pickler dynamic" should " be dynamicly symmetric " in {
		val json = human.pickle.value
		try {
			val result = json.unpickle[Figure]
			result should be(human)
		}catch{
			case b:Throwable => throw new Exception(s"I don't understand: $json", b)
		}
	}
	"pickler static" should " be staticly symmetric " in {
		import scala.pickling.Pickler
		import scala.pickling.Unpickler
		implicit val p = Pickler.generate[Figure]
		implicit val up = Unpickler.generate[Figure]
		val json = human.pickle.value
		try {
			val result = json.unpickle[Figure]
			result should be(human)
		}catch{
			case b:Throwable => throw new Exception(s"I don't understand: $json", b)
		}
	}
}

