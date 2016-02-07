package nl.jappieklooster.gdx.mapstare.controller

/**
 * A controller gets some T and returns a modified T
 * @tparam T
 */
trait Controller[T] {
	def apply(a:T):T
}
