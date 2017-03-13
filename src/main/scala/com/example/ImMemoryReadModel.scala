package com.example

import akka.actor.Props
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import redis.RedisClient

object ImMemoryReadModel extends App {

  val config = ConfigFactory.load()
    .withValue("akka.loglevel", ConfigValueFactory.fromAnyRef("OFF"))
    .withValue("akka.stdout-loglevel", ConfigValueFactory.fromAnyRef("OFF"))

  implicit val akkaSystem = akka.actor.ActorSystem("same-game-read-model", config)

  val channels = Seq("samegame:commits")

  akkaSystem
    .actorOf(Props(classOf[SubscribeActor], channels, Nil)
    .withDispatcher("rediscala.rediscala-client-worker-dispatcher"))

  println("Press Enter to terminate...")
  scala.io.StdIn.readLine()
  akkaSystem.terminate()
}

