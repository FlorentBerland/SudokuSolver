import model.{Region, Square}

import scala.annotation.tailrec

object Solvers {

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

  /*
    If a square has a single candidate, the candidate is the solution of the square
   */
  def resolveSingletons(grid: Set[Square]): Set[Square] = {
    grid.map(s => if(s.candidates.size == 1) s.solutionFound(s.candidates.head) else s)
  }

  /*
   Eliminates all the candidates that cannot be present in each square
  */
  def reduceCandidates(grid: Set[Square]): Set[Square] = {
    grid.map(square => {
      square.value match {
        case Left(_) => square
        case Right(candidates) =>
          val sbr = squaresByRegion(grid)
          square.valueUpdated(Right(square.regions.foldLeft(candidates)((candidates, region) => {
            sbr(region).foldLeft(candidates)((candidates, square) => {
            if(square.isSolved) candidates - square.value.left.get
            else candidates
          })
        })))
      }
    })
  }

  /*
    When a region contains a single square for a candidate, the square is the solution
   */
  def reduceSingletons(grid: Set[Square]): Set[Square] = {
    grid.map(square => square.value match {
      case Left(_) => square
      case Right(candidates) =>
        val sbr = squaresByRegion(grid)
        val singletonSet = candidates.foldLeft(Set.empty[Any])((singletonSet, candidate) => {
          square.regions.foldLeft(singletonSet)((singletonSet, region) => {
            // If the candidate is alone in the region, it is added to the set
            if(sbr(region).count(s => s.value.right.getOrElse(Set.empty).contains(candidate)) == 1)
              singletonSet + candidate
            else singletonSet
          })
        })
        if(singletonSet.size == 1) square.solutionFound(singletonSet.head)
        else square
    })
  }

  /*
    When two (resp. 3, 4, 5, ...) squares of the same region has the same two (resp. 3, 4, 5, ...) candidates
    but no other candidate, all these candidates are removed from the other squares of the region
   */
  def reduceTuples(grid: Set[Square]): Set[Square] = {
    def sameElements(square1: Square, square2: Square): Boolean = {
      @tailrec
      def sameElementsTR(set1: Set[Any], set2: Set[Any]): Boolean = {
        if (set2.isEmpty) set1.isEmpty
        else {
          set1.find(_ == set2.head) match {
            case Some(candidate) => sameElementsTR(set1 - candidate, set2.tail)
            case None => false
          }
        }
      }
      sameElementsTR(square1.candidates, square2.candidates)
    }

    def groupBy[T](col: Set[T], p: (T, T) => Boolean): Set[Set[T]] = {
      col.foldLeft(Map.empty[T, Set[T]])((map, elem) => {
        map.find(kv => p(kv._1, elem)) match {
          case Some(kv) => map.updated(kv._1, map(kv._1) + elem)
          case None => map + (elem -> Set(elem))
        }
      }).values.toSet
    }

    grid.map(square => {
      square.value match {
        case Left(_) => square
        case Right(candidates) =>
          val sbr = squaresByRegion(grid)
          square.valueUpdated(Right(
            square.regions.foldLeft(candidates)((candidates, region) => {
              // Each subset has n squares with the same n candidates (if the grid has a solution)
              groupBy(sbr(region), sameElements).filter(_.size > 1).foldLeft(candidates)((candidates, subset) => {
                // If the square is not in the subset, the candidates in the subset are removed from the square candidates
                if(!subset.contains(square)) candidates - subset.head.value.right.get
                else candidates
              })
            })
          ))
      }
    })
  }

  @tailrec
  // FIXME: The solver should stop if there is no progression between two iterations
  def solve(grid: Set[Square], algorithms: Compositor[Set[Square]]): Set[Square] = {
    if(!canContinue(grid)) grid
    else solve(algorithms(grid), algorithms)
  }
}
