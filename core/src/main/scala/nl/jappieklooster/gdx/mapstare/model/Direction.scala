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
