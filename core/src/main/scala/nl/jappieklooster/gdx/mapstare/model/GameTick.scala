package nl.jappieklooster.gdx.mapstare.model


/**
  * time can be a bitch, this will try to make it easier
  * Allows the gameticks to be extended later, for example with more advanced
  * date information.
  *
  * It also makes the code more readable, a passing a Float is less informative
  * than a gametick.
  */
case class GameTick(timeSinceLastFrame: Float) {

}
