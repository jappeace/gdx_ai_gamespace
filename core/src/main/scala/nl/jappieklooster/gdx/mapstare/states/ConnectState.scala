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

import java.net.{InetSocketAddress, InetAddress}

import akka.actor._
import akka.routing.RoundRobinRouter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.typesafe.config.ConfigFactory
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.akka._
import nl.jappieklooster.gdx.mapstare.akka.client.WorldStateUDPDeserializeActor
import nl.jappieklooster.gdx.mapstare.akka.server.{RegisterUpdateClient, WorldUpdateActor}
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
		container.add("Host IP")
		val hostIpField = factory.textField("127.0.0.1")
		container.add(hostIpField)
		container.row()
		container.add("my IP")
		val myIpField = factory.textField("127.0.0.1")
		container.add(myIpField)
		container.row()
		val host = factory.button("Host")
		val updateActorName = "updateActor"
		def changeState(inet:InetSocketAddress) = {
			game.updateActor ! RegisterUpdateClient(inet)
			stateMachine.changeTo(new BuildState(game))
		}
		val updateSys = "updateSystem"
		host.addListener(OnClick({
			val sys = ActorSystem(updateSys, ConfigFactory.load("host"))
			val updateActor =  sys.actorOf(Props[WorldUpdateActor], updateActorName)
			log.info(s"creating update actor: ${updateActor.path.toStringWithAddress(Address("akka.tcp", updateSys, "localhost", 2552))}")
			game.updateActor.actor = Option(sys.actorSelection(updateActor.path))
			val client = sys.actorOf(Props(new WorldStateUDPDeserializeActor(game)), WorldStateUDPDeserializeActor.name)
			import akka.io._

			changeState(WorldStateUDPDeserializeActor.localhost)
			updateActor ! WorldUpdateActor.Start
		}))
		val connect = factory.button("Connect")
		connect.addListener(OnClick({
			val sys = ActorSystem("clientSystem", ConfigFactory.load("client"))
			val ip = hostIpField.getText
			val my = myIpField.getText
			val port = 2552
			game.updateActor.actor = Option(sys.actorSelection(s"akka.tcp://$updateSys@$ip:$port/user/$updateActorName"))
			val client = sys.actorOf(Props(new WorldStateUDPDeserializeActor(game)), WorldStateUDPDeserializeActor.name)
			changeState(WorldStateUDPDeserializeActor.localhost)
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
