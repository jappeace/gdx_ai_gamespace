package nl.jappieklooster.gdx.mapstare.states

import nl.jappieklooster.gdx.mapstare.controller.Updateable
import nl.jappieklooster.gdx.mapstare.model.GameTick
import org.slf4j.LoggerFactory

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
