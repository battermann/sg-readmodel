package com.example

import akka.actor.Actor

class Game extends Actor {
  var board: Board = Board(Nil)
  var score = 0

  def receive = {
    case GameStarted(GameId(id), b) =>
      board = b
      println(s"[GAME $id] started")
      println(PrettyPrint.stringify(board))
    case GroupRemoved(GameId(id), b, sc) =>
      score = score + sc
      board = b
      println(s"[GAME $id] group removed")
      println(s"[GAME $id] $score point(s)")
      println(PrettyPrint.stringify(board))
    case GameFinished(GameId(id)) =>
      println(s"[GAME $id] no more moves, your final score is $score point(s)")
    case _ => ()
  }
}
