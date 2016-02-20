package nl.jappieklooster.gdx.mapstare

import java.lang.Thread

import _root_.akka.actor.{ActorSystem, Props}
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.{InputMultiplexer, Input, ApplicationAdapter, Gdx}
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, BitmapFont}
import akka.{ThreadIdentifier, ThreadIdentifyActor}
import nl.jappieklooster.gdx.mapstare.controller.Updater
import nl.jappieklooster.gdx.mapstare.input.gui.OnClick
import nl.jappieklooster.gdx.mapstare.input._
import nl.jappieklooster.gdx.mapstare.model._
import nl.jappieklooster.gdx.mapstare.states.{BuildState, StateMachine}
import nl.jappieklooster.gdx.mapstare.view.{Renderable, Animation}
import nl.jappieklooster.gdx.mapstare.controller._

import com.badlogic.gdx.math._
import org.slf4j.LoggerFactory

class Game() extends ApplicationAdapter {
	lazy val batch = new SpriteBatch()

	lazy val font = {
		val font = new BitmapFont()
		font.setColor(Color.BLACK)
		font
	}
	lazy val maprendeer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("map.tmx"))
	lazy val cam = Cam.cam
	lazy val stage = new Stage(new ScreenViewport(), batch)
	lazy val camMoveController = new CamMovement()

	lazy val animation = Animation.create(0.2f, 4, 227, 320, "swordman.png")(cam)
	lazy val selectionController = new SelectionBox()
	val updater = new Updater()
	val stateMachine = new StateMachine()
	val world = new World()
	lazy val plexer = new InputMultiplexer(camMoveController, stage, selectionController)

	// allow anything that has access to the game to render shit
	var customRenders:Seq[Renderable] = Nil

	override def create() = {
		// TODO: replace the plexer with an own variant which uses an enum map??
		stateMachine.changeTo(new BuildState(this))
		updater.targets = updater.targets :+ stateMachine :+ new WorldUpdater(world)
		Gdx.input.setInputProcessor(plexer)
	}

	var x = 0
	def update(timeSinceLast:GameTick): Unit ={
		updater.update(timeSinceLast)
		camMoveController.update(timeSinceLast)
		cam.cam.update()
	}
	val actorSystem = ActorSystem("fancypantsactors")
	lazy val actor =  actorSystem.actorOf(Props[ThreadIdentifyActor].withDispatcher("gdx-dispatcher"), "frame-actor")
	private val log = LoggerFactory.getLogger(classOf[Game])
	override def render() = {
		update(GameTick(Gdx.graphics.getDeltaTime))

		val pos = cam.mouseScreenPos()
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
		maprendeer.setView(cam.cam)
		maprendeer.render()
		batch.begin()
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			font.draw(batch, s"(${pos.x},${pos.y})", pos.x,  pos.y  )
		}
		world.units.foreach(animation.render(_, batch))
		customRenders.foreach(_.render(batch))

		batch.end()
		selectionController.render(batch)
		stage.act(Gdx.graphics.getDeltaTime)
		stage.draw()
	}

	override def resize (width:Int, height:Int):Unit = {
		println("resize")
		cam.toOrtho(width, height)
		stage.getViewport.update(width, height, true)
	}
}
