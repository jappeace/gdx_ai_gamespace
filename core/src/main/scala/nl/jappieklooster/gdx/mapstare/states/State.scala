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


package nl.jappieklooster.gdx.mapstare.states

import nl.jappieklooster.gdx.mapstare.controller.Updateable
import nl.jappieklooster.gdx.mapstare.model.GameTick
import org.slf4j.LoggerFactory

// TODO: does a State have to be updatable?
// could we handle the updates entirely with callbacks? which is arguably faster
// and eleganter..
trait State extends Updateable{
	def enter(stateMachine: StateMachine):Unit = {}
	def exit():Unit = {}
}
class StateMachine extends Updateable{
	private var currentState:Option[State] = None
	val log = LoggerFactory.getLogger(classOf[StateMachine])
	def changeTo(newState:State) = {
		for(current <- currentState){
			current.exit()
		}
		currentState = Some(newState)
		newState.enter(this)
	}

	/**
	  * @param tick
	  * @return should keep updating?
	  */
	override def update(tick: GameTick): Boolean = {
		var result = false
		for(st <-currentState) {
			result = st.update(tick)
		}
		if(!result){
			// maybe a bug
			log.debug("removing state-machine")
		}
		result
	}
}
