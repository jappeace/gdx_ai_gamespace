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

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import nl.jappieklooster.gdx.mapstare.Game
import nl.jappieklooster.gdx.mapstare.input.gui.OnClick
import nl.jappieklooster.gdx.mapstare.model.GameTick

/**
  * Connect or start a server
  */
class ConnectState(game:Game) extends GameState(game){
	override def enter(stateMachine: StateMachine):Unit = {
		val factory = new UIFactory()

		val container = factory.table()
		container.add("Connect to a host to start or host yourself!!!").colspan(2)
		container.row()
		container.add("IP")
		container.add(factory.textField("127.0.0.1"))
		container.row()
		val host = factory.button("Host")
		host.addListener(OnClick({
			println("click")
		}))
		val connect = factory.button("Connect")
		connect.addListener(OnClick({
			println("clack")
		}))
		container.add(host)
		container.add(connect)
		container.setWidth(400)
		container.setHeight(200)
		container.setPosition(Gdx.graphics.getWidth/2, Gdx.graphics.getHeight/2)
		stage.addActor(container)
	}
}
