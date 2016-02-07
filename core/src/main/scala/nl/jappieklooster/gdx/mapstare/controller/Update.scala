package nl.jappieklooster.gdx.mapstare.controller

trait Updateable {
	/**
	 *
	 * @param timeSinceLast
	 * @return should keep updating?
	 */
	def update(timeSinceLast:Float):Boolean
}
trait IntervalledUpdatable extends Updateable{
	private var frametime = 0.2f
	protected def setFrametime(to:Float) = frametime = to
	private var counter = 0f
	private var result = true
	override def update(timeSinceLast:Float):Boolean= {
		counter += timeSinceLast
		if(counter > frametime){
			counter -= frametime
			result = intervalledUpdate(timeSinceLast)
		}
		result
	}
	def intervalledUpdate(timeSinceLast:Float):Boolean
}
object Updateable{
	implicit def functionToUpdatable(func:(Float=>Boolean)):Updateable = new Updateable {
		override def update(timeSinceLast: Float): Boolean = func(timeSinceLast)
	}
}
class Updater(var targets:Seq[Updateable]=Nil) extends Updateable{
	def update(timeSinceLast:Float) = {
		targets = targets.filter(_.update(timeSinceLast))
		targets.nonEmpty
	}
}