package nl.jappieklooster.gdx.mapstare.states
import com.badlogic.gdx.{InputMultiplexer, Gdx}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.math.Vector2
import nl.jappieklooster.gdx.mapstare.Cam
import nl.jappieklooster.gdx.mapstare.controller.{Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input.gui.OnClick
import nl.jappieklooster.gdx.mapstare.input.{PlacementClick, SelectionBox, CamMovement}
import nl.jappieklooster.gdx.mapstare.model.{World, GameTick}
import nl.jappieklooster.gdx.mapstare.view.Animation
import org.slf4j.LoggerFactory

class FightState (world: World, stage: Stage)(implicit cam: Cam, inputMultiplexer: InputMultiplexer) extends State{
	val log = LoggerFactory.getLogger(classOf[FightState])
	/**
	  *
	  * @param tick
	  * @return should keep updating?
	  */
	override def update(tick: GameTick): Boolean = {
		log.info("Fighting!")
		true
	}
}
