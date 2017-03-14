package com.example

import akka.actor.{ActorRef, ActorSystem}
import redis.RedisClient

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object ReadModelBuilder {
  def rebuildProjection(host: String, port: Int, commitsKey: String, games: ActorRef)
                       (implicit ec: ExecutionContext, as: ActorSystem): Unit = {

    val redis = RedisClient(host = host, port = port)

    val result = for {
      storeRevision <- redis.hlen(commitsKey)
      commitIds = (0L until storeRevision).map(_.toString)
      maybeCommits <- if(commitIds.isEmpty) Future.successful(Nil) else redis.hmget(commitsKey, commitIds:_*)
    } yield maybeCommits.flatten

    result.foreach(_.foreach(message => games ! CommitDataMessage(message)))

    Await.result(result, Duration.Inf)
    println("read model was rebuild successfully")
  }
}
