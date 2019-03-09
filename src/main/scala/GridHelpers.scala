import model.{Region, Square}

import scala.annotation.tailrec

object GridHelpers {

  /**
    * Tests whether the resolution worth being continued
    *
    * @param grid The grid to check
    * @return True if the grid is not complete and is not jammed, false otherwise
    */
  def canContinue(grid: Set[Square]): Boolean = {
    def notComplete: Boolean = grid.exists(_.value.isRight)
    def notJammed: Boolean = grid.forall(s => s.value.isLeft || s.value.right.get.nonEmpty)
    notComplete && notJammed
  }

  def getRegions(grid: Set[Square]): Set[Region] = {
    grid.foldLeft(Set.empty[Region])((set, square) => set ++ square.regions)
  }

  def squaresInRegion(grid: Set[Square], region: Region): Set[Square] = {
    grid.foldLeft(Set.empty[Square])((set, square) => {
      if(square.regions.contains(region)) set + square
      else set
    })
  }

  def squaresByRegion(grid: Set[Square]): Map[Region, Set[Square]] = {
    getRegions(grid).map(r => (r, squaresInRegion(grid, r))).toMap
  }

  @tailrec
  // FIXME: The solver should stop if there is no progression between two iterations
  def solve(grid: Set[Square], algorithms: Compositor[Set[Square]]): Set[Square] = {
    if(!canContinue(grid)) grid
    else solve(algorithms(grid), algorithms)
  }

}
