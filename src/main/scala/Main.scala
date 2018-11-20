import java.awt.Point

import model.{Grid, Region, Square}
import ui.MainUI

object Main extends App {

  val columns = Array.fill(9)(new Region)
  val rows = Array.fill(9)(new Region)
  val blocks = Array.fill(3, 3)(new Region)
  val squares: Array[Array[Square]] = (0 until 9).map(i => (0 until 9).map(j => Square(new Point(i, j), None, null)).toArray).toArray

  squares(0)(0) = squares(0)(0).valueFound(7)
  squares(0)(1) = squares(0)(1).valueFound(2)
  squares(0)(5) = squares(0)(5).valueFound(6)
  squares(0)(7) = squares(0)(7).valueFound(8)
  squares(0)(8) = squares(0)(8).valueFound(1)
  squares(1)(0) = squares(1)(0).valueFound(5)
  squares(1)(1) = squares(1)(1).valueFound(4)
  squares(1)(3) = squares(1)(3).valueFound(1)
  squares(1)(5) = squares(1)(5).valueFound(7)
  squares(1)(6) = squares(1)(6).valueFound(6)
  squares(1)(8) = squares(1)(8).valueFound(2)
  squares(2)(1) = squares(2)(1).valueFound(3)
  squares(2)(4) = squares(2)(4).valueFound(8)
  squares(2)(5) = squares(2)(5).valueFound(9)
  squares(3)(2) = squares(3)(2).valueFound(1)
  squares(3)(3) = squares(3)(3).valueFound(6)
  squares(3)(6) = squares(3)(6).valueFound(4)
  squares(3)(8) = squares(3)(8).valueFound(9)
  squares(4)(0) = squares(4)(0).valueFound(4)
  squares(4)(1) = squares(4)(1).valueFound(9)
  squares(4)(3) = squares(4)(3).valueFound(5)
  squares(4)(5) = squares(4)(5).valueFound(8)
  squares(4)(7) = squares(4)(7).valueFound(6)
  squares(4)(8) = squares(4)(8).valueFound(7)
  squares(5)(0) = squares(5)(0).valueFound(2)
  squares(5)(2) = squares(5)(2).valueFound(7)
  squares(5)(5) = squares(5)(5).valueFound(4)
  squares(5)(6) = squares(5)(6).valueFound(5)
  squares(6)(3) = squares(6)(3).valueFound(8)
  squares(6)(4) = squares(6)(4).valueFound(4)
  squares(6)(7) = squares(6)(7).valueFound(2)
  squares(7)(0) = squares(7)(0).valueFound(6)
  squares(7)(2) = squares(7)(2).valueFound(4)
  squares(7)(3) = squares(7)(3).valueFound(9)
  squares(7)(5) = squares(7)(5).valueFound(3)
  squares(7)(7) = squares(7)(7).valueFound(7)
  squares(7)(8) = squares(7)(8).valueFound(5)
  squares(8)(0) = squares(8)(0).valueFound(9)
  squares(8)(1) = squares(8)(1).valueFound(8)
  squares(8)(3) = squares(8)(3).valueFound(7)
  squares(8)(7) = squares(8)(7).valueFound(4)
  squares(8)(8) = squares(8)(8).valueFound(3)

  squares.indices.foreach(i => squares(i).indices.foreach(j => {
    val square = squares(i)(j)
    squares(i)(j) = Square(square.coordinates, square.value,
      Set(columns(i), rows(j), blocks(i / 3)(j / 3))
    )
  }))
  val grid = Grid(squares.flatten.toSet)
  val compositor = Compositor[Grid](Solvers.reduceSingletons, Solvers.reduceCandidates)
  val emptyCompositor = Compositor[Grid]()
  val ui = new MainUI(Solvers.solve(grid, compositor))

}
