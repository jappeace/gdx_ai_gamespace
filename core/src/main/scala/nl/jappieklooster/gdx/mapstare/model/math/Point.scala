package nl.jappieklooster.gdx.mapstare.model.math
import com.badlogic.gdx.math.Vector2

/**
  * Immutable point in a plane
  * @param x
  * @param y
  */
case class Point(x:Float, y:Float) {
	def +(v: Point) = Point(x + v.x, y + v.y)
	def -(v: Point) = Point(x - v.x, y - v.y)
	def *(v: Point) = Point(x * v.x, y * v.y)
	def /(v: Point) = Point(x / v.x, y / v.y)
	def unary_- = negate

	def scale(to:Float) = transform(x => x*to)

	/**
	  * apply the same operation to all the elements of the vector and create
	  * a new vector with that
	  * @param wit the function to apply
	  * @return
	  */
	def transform(wit : (Float) => Float) = Point(wit(x), wit(y))

	/**
	  * return either yourself if already absolute or a new absoulte
	  * Vector, keep the ref (because immutable)
	  */
	lazy val abs = if((Math.abs(x) == x) && (Math.abs(y) == y)){
		this
	}else{
		transform(Math.abs)
	}

	lazy val negate = Point(-x, -y)

	lazy val lengthSq = x*x + y*y
	lazy val length = Math.sqrt(lengthSq).toFloat
	lazy val normal = transform(x => x * (1/length))
}
object Point{
	val zero = Point(0,0)
	val id = Point(1,1)
	val idX = Point(1,0)
	val idY = Point(0,1)

	/*
	 *  implicit conversion from and to JME3 mutable vectors.
	 *  Its probably slow, but micro optimastions can be inserted for the
	 *  imutable ones rather easily.
	 */
	implicit def toMutable(v:Point):Vector2 = new Vector2(v.x,v.y)
	implicit def fromMutable(v:Vector2):Point = Point(v.x,v.y)

	/**
	  * allows doing cool stuf such as Point(3,3,3)/3 without defining
	  * more methods.
	  * @param v
	  * @return
	  */
	implicit def fromFloat(v:Float):Point = Point(v,v)
	implicit def fromInt(v:Int):Point = Point(v,v)
}
