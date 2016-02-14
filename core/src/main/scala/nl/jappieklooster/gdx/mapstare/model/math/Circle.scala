package nl.jappieklooster.gdx.mapstare.model.math
import com.badlogic.gdx.math.MathUtils

/**
  * Immutable circle
  * @param position
  * @param radius
  */
case class Circle(position: Point, radius:Float) {
	def contains(point: Point) = {
		val x = position.x - point.x
		val y = position.y - point.y
		x * x + y * y <= radius * radius
	}

	def contains(c: Circle): Boolean = {
		val radiusDiff: Float = radius - c.radius
		if (radiusDiff < 0f) return false
		val dx: Float = position.x - c.position.x
		val dy: Float = position.y - c.position.y
		val dst: Float = dx * dx + dy * dy
		val radiusSum: Float = radius + c.radius
		!(radiusDiff * radiusDiff < dst) && (dst < radiusSum * radiusSum)
	}

	def overlaps(c: Circle): Boolean = {
		val dx: Float = position.x - c.position.x
		val dy: Float = position.y - c.position.y
		val distance: Float = dx * dx + dy * dy
		val radiusSum: Float = radius + c.radius
		distance < radiusSum * radiusSum
	}

	lazy val circumference: Float = this.radius * MathUtils.PI2
	lazy val area: Float = this.radius * this.radius * MathUtils.PI
}
