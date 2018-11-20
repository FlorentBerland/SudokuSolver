import model.{Grid, Square}

import scala.annotation.tailrec

object Solvers {

  /*
    Eliminates all the candidates that cannot be present in each square
   */
  def findCandidates(grid: Grid): Grid = {
    Grid(grid.squares.map(s => s.valueChanged(
      (s.value match {
        case None => Some(Right(Square.getValues))
        case some => some
      }).map(value => {
        value match {
          case Right(candidates) =>
            Right(s.regions.foldLeft(candidates)((candidates, region) => {
              candidates diff grid.getValues(region)
            }))
          case left => left
        }
      }))
    ))
  }

  /*
    If a square has a single candidate, the candidate is the solution of the square
   */
  def reduceCandidates(grid: Grid): Grid = {
    Grid(grid.squares.map(s => s.valueChanged(
      s.value.map(either => either match {
          case Right(candidates) =>
            if(candidates.size == 1) Left(candidates.head)
            else Right(candidates)
          case left => left
        }
      ))
    ))
  }

  /*
    When a region contains a single square for a candidate, the square is the solution
   */
  def reduceSingletons(grid: Grid): Grid = {
    val squaresByRegion = grid.getRegions.map(region => grid.getSquares(region))
    val reducedSquares = squaresByRegion.flatMap(squareSet => {
      Square.getValues.flatMap(value => {
        // Count the number of occurrences of the candidate value in the region
        val count = squareSet.count(_.candidates.contains(value))
        // If there is a single candidate, assign it to the square that contains it
        if(count == 1) squareSet.map(s => if(s.candidates.contains(value)) s.valueFound(value) else s)
        else squareSet
      })
    })

    val mergedSequences = (new ((Set[Square], Set[Square]) => Set[Square]) {
      @tailrec
      def apply(newSeq: Set[Square], oldSeq: Set[Square]): Set[Square] = {
        if (oldSeq.isEmpty) newSeq
        else {
          val head = oldSeq.head
          newSeq.find(_.coordinates.equals(head.coordinates)) match {
            case Some(square) =>
              if (square.isSolved) apply(newSeq, oldSeq.tail)
              else apply(newSeq - square + head, oldSeq.tail)
            case None => apply(newSeq + head, oldSeq.tail)
          }
        }
      }
    })(Set.empty, reducedSquares)

    Grid(mergedSequences)
  }

  /*
    When two (resp. 3, 4, 5, ...) squares of the same region has the same two (resp. 3, 4, 5, ...) candidates
    but no other candidate, all these candidates are removed from the other squares of the region
   */
  def reduceTuples(grid: Grid): Grid = {
    def sameElements(square1: Square, square2: Square): Boolean = {
      @tailrec
      def sameElementsTR(set1: Set[Int], set2: Set[Int]): Boolean = {
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
      var map = Map.empty[T, Set[T]]
      col.foreach(elem => {
        map.find(kv => p(kv._1, elem)) match {
          case Some(kv) => map = map + (kv._1 -> (map(kv._1) + elem))
          case None => map = map + (elem -> Set(elem))
        }
      })
      map.values.toSet
    }

    val squaresByRegion = grid.getRegions.map(region => grid.getSquares(region))
    val a = squaresByRegion.map(squaresInRegion => {
      // If many squares have the same candidates, they are grouped in the same set
      val squaresGroupedByCandidates = groupBy[Square](squaresInRegion, sameElements)
      // Keep only the sets that have as many squares as candidates and merge it into a single set
      val filteredGroupedSquares = squaresGroupedByCandidates.filter(kv => kv.size == kv.head.candidates.size).flatten
      // For each set, remove all the others candidates from the others squares
      squaresInRegion.map(square => if(!square.isSolved && !filteredGroupedSquares.contains(square)){
        0
      })
      0
    })

    grid
  }

  @tailrec
  // FIXME: The solver should stop if there is no progression between two iterations
  def solve(grid: Grid, algorithms: Compositor[Grid]): Grid = {
    if(grid.isComplete) grid
    else {
      val fullGrid = findCandidates(grid)
      if(!fullGrid.hasTrivialSolution) grid
      else solve(algorithms(fullGrid), algorithms)
    }
  }
}
