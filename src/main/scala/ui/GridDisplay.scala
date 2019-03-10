package ui

import java.awt._
import model.Square
import scala.collection.immutable.List

class GridDisplay(_grid: List[Square], allValues: Set[Any]) extends Container {

  var $grid: List[Square] = _grid

  init()

  def grid: List[Square] = $grid
  def grid_$eq(g: List[Square]): Unit = {
    $grid = g
    this.paint(this.getGraphics)
  }

  private def init(): Unit = {
    val width = Math.sqrt(grid.size).toInt
    val height = grid.size / width
    val squareWidth = getPreferredSize.width / width
    val squareHeight = getPreferredSize.height / height
    setLayout(new GridLayout(width, height))
    grid.foreach(square => add(
      new SquareDisplay(square, allValues){
        setPreferredSize(new Dimension(squareWidth, squareHeight))
      }
    ))
  }

}
