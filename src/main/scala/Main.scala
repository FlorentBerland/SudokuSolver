import dao.ClassicSudokuCSV
import model.Square
import ui.MainUI

import scala.language.postfixOps

object Main extends App {

  val grid = new ClassicSudokuCSV("medium.csv").load()
  val compositor = Compositor[List[Square]](Solvers.reduceCandidates, Solvers.reduceTuples, Solvers.resolveHiddenSingletons, Solvers.resolveSingletons)
  val ui = new MainUI(GridHelpers.solve(grid, compositor), (1 to 9).toSet)

}
