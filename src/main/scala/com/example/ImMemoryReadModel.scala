package com.example

import akka.actor.Props
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}

import scala.concurrent.ExecutionContext.Implicits.global

object ImMemoryReadModel extends App {

  val config = ConfigFactory.load()
    .withValue("akka.loglevel", ConfigValueFactory.fromAnyRef("OFF"))
    .withValue("akka.stdout-loglevel", ConfigValueFactory.fromAnyRef("OFF"))

  implicit val akkaSystem = akka.actor.ActorSystem("same-game-read-model", config)

  val channels = Seq("samegame:commits")

  val games = akkaSystem.actorOf(Props(classOf[Games]), name = "Games")

  akkaSystem
    .actorOf(Props(classOf[Subscriber], games, channels, Nil)
      .withDispatcher("rediscala.rediscala-client-worker-dispatcher"), name = "Subscriber")

  ReadModelBuilder.rebuildProjection("localhost", 6379, "samegame:commits", games)

  println("Press Enter to terminate...")
  scala.io.StdIn.readLine()
  akkaSystem.terminate()
}

