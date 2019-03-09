import model.{Region, Square}

import scala.annotation.tailrec

object GridHelpers {

  /**
    * Tests whether the resolution worth being continued
    *
    * @param grid The grid to check
    * @return True if the grid is not complete and is not jammed, false otherwise
    */
  def canContinue(grid: List[Square]): Boolean = {
    def notComplete: Boolean = grid.exists(_.value.isRight)
    def notJammed: Boolean = grid.forall(s => s.value.isLeft || s.value.right.get.nonEmpty)
    notComplete && notJammed
  }

  def getRegions(grid: List[Square]): Set[Region] = {
    grid.foldLeft(Set.empty[Region])((set, square) => set ++ square.regions)
  }

  def squaresInRegion(grid: List[Square], region: Region): List[Square] = {
    grid.foldLeft(List.empty[Square])((list, square) => {
      if(square.regions.contains(region)) square +: list
      else list
    })
  }

  def squaresByRegion(grid: List[Square]): Map[Region, List[Square]] = {
    getRegions(grid).map(r => (r, squaresInRegion(grid, r))).toMap
  }

  @tailrec
  // FIXME: The solver should stop if there is no progression between two iterations
  def solve(grid: List[Square], algorithms: Compositor[List[Square]]): List[Square] = {
    if(!canContinue(grid)) grid
    else solve(algorithms(grid), algorithms)
  }

}
