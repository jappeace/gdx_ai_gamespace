package nl.jappieklooster.gdx.mapstare.states
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.model.GameTick

/**
  * Instead of passing a whole collections of attributes arround we just
  * jammed everything in game for convienence, then we make it
  * globally accsable from this class. Architecture be dammed (although
  * this is probably a good compromise between convenience and making
  * everything static)
  *
  * Besides the gamestates aren't meant to do encapsulation. Instead they're
  * meant to group logic together and make transitions save.
 *
  * @param game
  */
abstract class GameState(game:Game) extends State {
	val world = game.world
	val stage = game.stage
	val cam = game.cam
	val inputMultiplexer = game.plexer
	/**
	  *
	  * @param tick
	  * @return should keep updating?
	  */
	override def update(tick: GameTick): Boolean = {
		true
	}
}
