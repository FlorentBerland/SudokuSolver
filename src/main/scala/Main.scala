import dao._
import model.Square
import ui.MainUI

import scala.language.postfixOps

object Main extends App {

  val grid = new SpecialRegionLoaderCSV("grids/hard.csv", "regions/classic.csv").load()
  val compositor = Compositor[List[Square]](Solvers.reduceCandidates, Solvers.reduceTuples, Solvers.resolveHiddenSingletons, Solvers.resolveSingletons)
  new MainUI(GridHelpers.solve(grid, compositor), (1 to 9).toSet)

}
