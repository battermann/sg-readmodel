package com.example

import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets

import julienrf.json.derived
import play.api.libs.json.{Format, Json}
import redis.actors.RedisSubscriberActor
import redis.api.pubsub.{Message, PMessage}
import com.example.serialization.implicits._

class SubscribeActor(channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
  extends RedisSubscriberActor(
    new InetSocketAddress("localhost", 6379),
    channels,
    patterns,
    onConnectStatus = connected => { println(s"connected: $connected")}
  ) {

  case class CommitData(storeRevision: Int, timestamp: Long, streamId: String, streamRevision: Int, events: Seq[Event])

  implicit val commitDataFormat: Format[CommitData] = derived.oformat[CommitData]()

  def onMessage(message: Message) {
    val data = message.data.decodeString(StandardCharsets.UTF_8)
    val commitData = Json.parse(data).as[CommitData]
    commitData.events.foreach(e => println(s"[EVENT]: $e"))
  }

  def onPMessage(pmessage: PMessage) {
  }
}
