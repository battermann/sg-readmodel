package com.example

import java.util.UUID

sealed trait Color
case object Green extends Color
case object Blue extends Color
case object Red extends Color
case object Brown extends Color
case object Gray extends Color

sealed trait CellState
case class Filled(color: Color) extends CellState
case object Empty extends CellState

case class Column(cells: List[CellState]) extends AnyVal
case class Board private (columns: List[Column]) extends AnyVal

sealed trait Game
case object Uninitialized extends Game
case class InProgress(board: Board, score: Int) extends Game
case class Finished(board: Board, score: Int) extends Game

case class GameId(id: UUID) extends AnyVal

sealed trait Event
case class GameStarted(id: GameId, board: Board) extends Event
case class GroupRemoved(id: GameId, board: Board, score: Int) extends Event
case class GameFinished(id: GameId) extends Event
