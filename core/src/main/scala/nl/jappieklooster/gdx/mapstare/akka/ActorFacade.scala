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


package nl.jappieklooster.gdx.mapstare.akka

import akka.actor.{ActorSelection, ScalaActorRef, ActorRef}
import org.slf4j.LoggerFactory

/**
  * Place holder for where should be an actor (its a fancy option with logging)
  */
class ActorFacade{
	var actor:Option[ActorSelection] = None
	val log = LoggerFactory.getLogger(classOf[ActorFacade])
	def !(message:Any) = actor match{
		case Some(x) => x ! message
		case None => log.warn(s"message $message not send")
	}
}
