package nl.jappieklooster.gdx.mapstare

trait Updateable {
	def update(timeSinceLast:Float):Unit = {}
}
trait IntervalledUpdatable extends Updateable{
	private var frametime = 0.2f
	protected def setFrametime(to:Float) = frametime = to
	private var counter = 0f
	override def update(timeSinceLast:Float):Unit = {
		counter += timeSinceLast
		if(counter > frametime){
			counter -= frametime
			intervalledUpdate(timeSinceLast)
		}
	}
	def intervalledUpdate(timeSinceLast:Float)
}