import java.awt.Point

import model.{Region, Square}
import ui.MainUI

object Main extends App {

  /*
  val columns = Array.fill(9)(new Region)
  val rows = Array.fill(9)(new Region)
  val blocks = Array.fill(3, 3)(new Region)
  val squares: Array[Array[Square]] = (0 until 9).map(i => (0 until 9).map(j => Square(new Point(i, j), None, null)).toArray).toArray

  squares(0)(0) = squares(0)(0).solutionFound(7)
  squares(0)(1) = squares(0)(1).solutionFound(2)
  squares(0)(5) = squares(0)(5).solutionFound(6)
  squares(0)(7) = squares(0)(7).solutionFound(8)
  squares(0)(8) = squares(0)(8).solutionFound(1)
  squares(1)(0) = squares(1)(0).solutionFound(5)
  squares(1)(1) = squares(1)(1).solutionFound(4)
  squares(1)(3) = squares(1)(3).solutionFound(1)
  squares(1)(5) = squares(1)(5).solutionFound(7)
  squares(1)(6) = squares(1)(6).solutionFound(6)
  squares(1)(8) = squares(1)(8).solutionFound(2)
  squares(2)(1) = squares(2)(1).solutionFound(3)
  squares(2)(4) = squares(2)(4).solutionFound(8)
  squares(2)(5) = squares(2)(5).solutionFound(9)
  squares(3)(2) = squares(3)(2).solutionFound(1)
  squares(3)(3) = squares(3)(3).solutionFound(6)
  squares(3)(6) = squares(3)(6).solutionFound(4)
  squares(3)(8) = squares(3)(8).solutionFound(9)
  squares(4)(0) = squares(4)(0).solutionFound(4)
  squares(4)(1) = squares(4)(1).solutionFound(9)
  squares(4)(3) = squares(4)(3).solutionFound(5)
  squares(4)(5) = squares(4)(5).solutionFound(8)
  squares(4)(7) = squares(4)(7).solutionFound(6)
  squares(4)(8) = squares(4)(8).solutionFound(7)
  squares(5)(0) = squares(5)(0).solutionFound(2)
  squares(5)(2) = squares(5)(2).solutionFound(7)
  squares(5)(5) = squares(5)(5).solutionFound(4)
  squares(5)(6) = squares(5)(6).solutionFound(5)
  squares(6)(3) = squares(6)(3).solutionFound(8)
  squares(6)(4) = squares(6)(4).solutionFound(4)
  squares(6)(7) = squares(6)(7).solutionFound(2)
  squares(7)(0) = squares(7)(0).solutionFound(6)
  squares(7)(2) = squares(7)(2).solutionFound(4)
  squares(7)(3) = squares(7)(3).solutionFound(9)
  squares(7)(5) = squares(7)(5).solutionFound(3)
  squares(7)(7) = squares(7)(7).solutionFound(7)
  squares(7)(8) = squares(7)(8).solutionFound(5)
  squares(8)(0) = squares(8)(0).solutionFound(9)
  squares(8)(1) = squares(8)(1).solutionFound(8)
  squares(8)(3) = squares(8)(3).solutionFound(7)
  squares(8)(7) = squares(8)(7).solutionFound(4)
  squares(8)(8) = squares(8)(8).solutionFound(3)

  squares.indices.foreach(i => squares(i).indices.foreach(j => {
    val square = squares(i)(j)
    squares(i)(j) = Square(square.coordinates, square.value,
      Set(columns(i), rows(j), blocks(i / 3)(j / 3))
    )
  }))
  val grid = squares.flatten.toSet
  val compositor = Compositor[Set[Any]](Solvers.reduceSingletons, Solvers.resolveSingletons)
  val emptyCompositor = Compositor[Set[Any]]()
  val ui = new MainUI(Solvers.solve(grid, compositor))
*/
}
