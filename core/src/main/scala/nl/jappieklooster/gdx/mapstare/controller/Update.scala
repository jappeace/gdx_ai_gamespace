package nl.jappieklooster.gdx.mapstare.controller

import nl.jappieklooster.gdx.mapstare.model.GameTick

trait Updateable {
	/**
	 *
	 * @param tick
	 * @return should keep updating?
	 */
	def update(tick:GameTick):Boolean
}
trait IntervalledUpdatable extends Updateable{
	private var frametime = 0.2f
	protected def setFrametime(to:Float) = frametime = to
	private var counter = 0f
	private var result = true
	override def update(tick:GameTick):Boolean= {
		counter += tick.timeSinceLastFrame
		if(counter > frametime){
			counter -= frametime
			result = intervalledUpdate(tick)
		}
		result
	}
	def intervalledUpdate(timeSinceLast:GameTick):Boolean
}
object Updateable{
	implicit def functionToUpdatable(func:(GameTick=>Boolean)):Updateable = new Updateable {
		override def update(timeSinceLast: GameTick): Boolean = func(timeSinceLast)
	}
}
class Updater(var targets:Seq[Updateable]=Nil) extends Updateable{
	def update(timeSinceLast:GameTick) = {
		targets = targets.filter(_.update(timeSinceLast))
		targets.nonEmpty
	}
}