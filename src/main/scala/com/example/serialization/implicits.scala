package com.example.serialization

import com.example._
import julienrf.json.derived
import play.api.libs.json.Format

object implicits {
  implicit val gameIdFormat: Format[GameId] = derived.oformat[GameId]()
  implicit val colorFormat: Format[Color] = derived.oformat[Color]()
  implicit val cellStateFormat: Format[CellState] = derived.oformat[CellState]()
  implicit val columnFormat: Format[Column] = derived.oformat[Column]()
  implicit val boardFormat: Format[Board] = derived.oformat[Board]()
  implicit val eventFormat: Format[Event] = derived.oformat[Event]()
  implicit val gameFormat: Format[Game] = derived.oformat[Game]()
}
