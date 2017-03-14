package com.example

import java.nio.charset.StandardCharsets

import akka.actor.{Actor, Props}
import akka.util.ByteString
import play.api.libs.json.Json
import com.example.serialization.implicits._

case class CommitDataMessage(data: ByteString)

class Games extends Actor {

  def receive = {
    case CommitDataMessage(message) =>
      val data = message.decodeString(StandardCharsets.UTF_8)
      val commitData = Json.parse(data).as[CommitData]
      commitData.events.foreach {
        case e@GameStarted(_, _) =>
          val game = context.actorOf(Props[Game], name = s"${commitData.streamId}")
          game ! e
        case e =>
          val game = context.actorSelection(s"/user/Games/${commitData.streamId}")
          game ! e
      }
    case _ => ()
   }
}
