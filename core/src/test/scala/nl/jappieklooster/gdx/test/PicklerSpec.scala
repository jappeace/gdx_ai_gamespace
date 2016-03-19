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

