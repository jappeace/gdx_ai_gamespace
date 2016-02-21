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

/** modified https://gist.github.com/viktorklang/2422443
  * A dispatcher that forces an actor to run on the Gdx main thread
  */
import java.util.Collections
import java.util.concurrent.{AbstractExecutorService, ExecutorService, ThreadFactory, TimeUnit}

import akka.dispatch.{DispatcherPrerequisites, ExecutorServiceConfigurator, ExecutorServiceFactory}
import com.badlogic.gdx.Gdx
import com.typesafe.config.Config

/**
  * The design of abstract executer service allows for multiple threads to be
  * managed. We don't want that. UI thread is a single thread (except in vulcan
  * but we'll jump that bridge when we come to it). So we just make the entire
  * thing static.
  * @param command
  */
object GdxExecutorService extends AbstractExecutorService {
	def execute(command: Runnable) = Gdx.app.postRunnable(command)

	// all these commands are useless for a render thread. so we'll just stub
	// them.
	def shutdown(): Unit = ()
	def shutdownNow() = Collections.emptyList[Runnable]
	def isShutdown = false
	def isTerminated = false
	def awaitTermination(l: Long, timeUnit: TimeUnit) = true

	// again a factory doesn't even makes sense, in this case. so we just static
	// this too.
	val factory = new ExecutorServiceFactory {
		def createExecutorService: ExecutorService = GdxExecutorService
	}
}

// Then we create an ExecutorServiceConfigurator so that Akka can use our SwingExecutorService for the dispatchers
class GdxExecConfigurer(
		config: Config,
		prerequisites: DispatcherPrerequisites
) extends ExecutorServiceConfigurator(config, prerequisites) {
	def createExecutorServiceFactory(
			id: String,
			threadFactory: ThreadFactory
	): ExecutorServiceFactory = GdxExecutorService.factory
}
