// This is a playground for AI, it offers a game space for AI to work with.
// Copyright (C) 2016 Jappe Klooster

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.If not, see <http://www.gnu.org/licenses/>.


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