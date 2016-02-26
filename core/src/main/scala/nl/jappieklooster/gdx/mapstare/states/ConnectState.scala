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

import akka.actor.{Address, ActorSystem, Props}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.typesafe.config.ConfigFactory
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.akka.{RegisterUpdateClient, UpdateClient, WorldUpdateActor}
import nl.jappieklooster.gdx.mapstare.controller.{Updateable, Updater}
import nl.jappieklooster.gdx.mapstare.input.gui.OnClick
import nl.jappieklooster.gdx.mapstare.model.GameTick
import org.slf4j.LoggerFactory

/**
  * Connect or start a server
  */
class ConnectState(game:Game) extends GameState(game){

	val log = LoggerFactory.getLogger(classOf[ConnectState])

	override def enter(stateMachine: StateMachine):Unit = {
		val factory = new UIFactory()

		val container = factory.table()
		container.add("Connect to a host to start or host yourself!!!").colspan(2)
		container.row()
		container.add("IP")
		val ipField = factory.textField("127.0.0.1")
		container.add(ipField)
		container.row()
		val host = factory.button("Host")
		val updateActorName = "updateActor"
		def registerClient(system:ActorSystem) = {
			val updateClientRef = system.actorOf(Props(new UpdateClient(game)).withDispatcher("gdx-dispatcher"), UpdateClient.name)
			game.updateActor ! RegisterUpdateClient(updateClientRef.path)
			stateMachine.changeTo(new BuildState(game))

		}
		val updateSys = "updateSystem"
		host.addListener(OnClick({
			val actorSystem = ActorSystem(updateSys, ConfigFactory.load("host"))
			val updateActor =  actorSystem.actorOf(Props[WorldUpdateActor], updateActorName)
			log.info(s"creating update actor: ${updateActor.path.toStringWithAddress(Address("akka.tcp", updateSys, "localhost", 2552))}")
			game.updater.add((tick:GameTick) => {
				updateActor ! tick
				true
			})
			game.updateActor.actor = Option(actorSystem.actorSelection(updateActor.path))
			registerClient(actorSystem)
		}))
		val connect = factory.button("Connect")
		connect.addListener(OnClick({
			val actorSystem = ActorSystem("clientSystem", ConfigFactory.load("client"))
			val ip = ipField.getText
			game.updateActor.actor = Option(actorSystem.actorSelection(s"akka.tcp://$updateSys@$ip:2552/user/$updateActorName"))
			registerClient(actorSystem)
		}))
		container.add(host)
		container.add(connect)
		container.setWidth(400)
		container.setHeight(200)
		container.setPosition(Gdx.graphics.getWidth/2, Gdx.graphics.getHeight/2)
		stage.addActor(container)
	}
	override def exit():Unit = {
		stage.getRoot.clear()
	}
}
