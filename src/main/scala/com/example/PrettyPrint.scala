package com.example

object PrettyPrint {
  private def makeCell(c: CellState) = {
    c match {
      case Filled(color) =>
        color match {
          case Red => "1"
          case Green => "2"
          case Blue => "3"
          case Gray => "4"
          case Brown => "5"
        }
      case Empty => " "
    }
  }

  def stringify(board: Board): String = {

    val w = board.columns.length
    val h = board.columns.head.cells.length

    (1 to h)
      .map(h - _)
      .map(r => (0 until w).map(c => makeCell(board.columns(c).cells(r))).mkString("|"))
      .mkString("\n")
  }
}
