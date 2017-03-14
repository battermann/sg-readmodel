package com.example

import java.net.InetSocketAddress

import akka.actor.ActorRef
import redis.actors.RedisSubscriberActor
import redis.api.pubsub.{Message, PMessage}

class Subscriber(games: ActorRef, channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
  extends RedisSubscriberActor(
    new InetSocketAddress("localhost", 6379),
    channels,
    patterns,
    onConnectStatus = connected => { println(s"connected: $connected")}
  ) {

  def onMessage(message: Message) {
    games ! CommitDataMessage(message.data)
  }

  def onPMessage(pmessage: PMessage) {
  }
}
