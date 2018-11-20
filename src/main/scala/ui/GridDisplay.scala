package ui

import java.awt._

import model.Grid

class GridDisplay(_grid: Grid) extends Container {

  var $grid: Grid = _grid

  init()

  def grid: Grid = $grid
  def grid_$eq(g: Grid): Unit = {
    $grid = g
    this.paint(this.getGraphics)
  }

  private def init(): Unit = {
    val minX = grid.squares.minBy(_.coordinates.x).coordinates.x
    val maxX = grid.squares.maxBy(_.coordinates.x).coordinates.x
    val minY = grid.squares.minBy(_.coordinates.y).coordinates.y
    val maxY = grid.squares.maxBy(_.coordinates.y).coordinates.y
    val squareWidth = this.getPreferredSize.width / (maxX - minX + 1)
    val squareHeight = this.getPreferredSize.height / (maxY - minY + 1)

    val squares = grid.squares.toArray.sortWith((s1, s2) => {
      (s1.coordinates.y < s2.coordinates.y) || (s1.coordinates.y == s2.coordinates.y && s1.coordinates.x < s2.coordinates.x)
    }).map(s => new SquareDisplay(s){ setPreferredSize(new Dimension(squareWidth, squareHeight))})
    this.setLayout(new GridLayout(maxX - minX + 1, maxY - minY + 1))
    squares.foreach(s => this.add(s))
  }

}
