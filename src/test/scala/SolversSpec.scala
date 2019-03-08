import java.awt.Point

import model.{Region, Square}
import org.scalatest.WordSpec

class SolversSpec extends WordSpec {

  private val region = new Region()
  private val squaresPartial = (1 to 8).map(i => Square(new Point(1, i), None, Set(region))).toSet
  private val squaresPartial2 = (1 to 8).map(i => Square(new Point(1, i), Some(Right((1 to 8).toSet)), Set(region))).toSet
  private val grid = Grid(squaresPartial + Square(new Point(1, 9), Some(Left(9)), Set(region)))
  private val grid2 = Grid(squaresPartial + Square(new Point(1, 9), Some(Right(Set(9))), Set(region)))
  private val grid3 = Grid(squaresPartial2 + Square(new Point(1, 9), Some(Right((1 to 9).toSet)), Set(region)))

  assert(grid.getValues(region).contains(9))
  assert(grid.getRegions.contains(region))
  assert(grid.hasTrivialSolution)
  assert(!grid.isComplete)
  assert(Solvers.reduceCandidates(grid).getSquares(region).forall(!_.candidates.contains(9)))
  assert(Solvers.resolveSingletons(grid2).getValues(region).contains(9))
  assert(Solvers.reduceSingletons(grid3).getValues(region).contains(9))

}
