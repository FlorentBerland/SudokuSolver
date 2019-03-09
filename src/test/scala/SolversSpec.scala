import model.{Region, Square}
import org.scalatest.WordSpec

class SolversSpec extends WordSpec {

  "A square" when {
    "has a single candidate" should {
      "be solved" in {
        val square = Square(Right[Any, Set[Any]](Set(4)))
        assert(Solvers.resolveHiddenSingletons(List(square)).head.isSolved)
      }
      "have the candidate as solution" in {
        val square = Square(Right[Any, Set[Any]](Set(4)))
        assert(Solvers.resolveHiddenSingletons(List(square)).head.value.left.get == 4)
      }
    }
    "has multiple candidates" should {
      "not be solved" in {
        val square = Square(Right[Any, Set[Any]](Set(4, 5)))
        assert(!Solvers.resolveHiddenSingletons(List(square)).head.isSolved)
      }
    }

    "has a candidate which is unique in the region" should {
      "be solved" in {
        val region = new Region
        val squareWithUniqueCandidate = Square(Right[Any, Set[Any]](Set(4, 5, 6)), Set(region))
        val squareNotSolvable = Square(Right[Any, Set[Any]](Set(4, 6)), Set(region))
        val grid = List(squareWithUniqueCandidate, squareNotSolvable)
        assert(Solvers.resolveSingletons(grid).head.isSolved)
      }
      "have its candidate as the solution" in {
        val region = new Region
        val squareWithUniqueCandidate = Square(Right[Any, Set[Any]](Set(4, 5, 6)), Set(region))
        val squareNotSolvable = Square(Right[Any, Set[Any]](Set(4, 6)), Set(region))
        val grid = List(squareWithUniqueCandidate, squareNotSolvable)
        assert(Solvers.resolveSingletons(grid).head.value.left.get == 5)
      }
    }

    "in the same region as a solved square" should {
      "have its candidate equal the solved square eliminated" in {
        val region = new Region
        val unsolvedSquare = Square(Right[Any, Set[Any]](Set(4, 5)), Set(region))
        val solvedSquare = Square(Left(4), Set(region))
        val grid = List(unsolvedSquare, solvedSquare)
        val reducedGrid = Solvers.reduceCandidates(grid)
        assert(!reducedGrid.head.value.right.get.contains(4))
        assert(reducedGrid.head.value.right.get.contains(5))
      }


      "is in a region that contains a tuple of squares with exactly the same candidates" should {
        "remove all the candidates equal these in the tuple" in {
          val region = new Region
          val squareTuple1 = Square(Right[Any, Set[Any]](Set(1, 2, 3)), Set(region))
          val squareTuple2 = Square(Right[Any, Set[Any]](Set(3, 1, 2)), Set(region))
          val squareTuple3 = Square(Right[Any, Set[Any]](Set(1, 3, 2)), Set(region))
          val squareOther = Square(Right[Any, Set[Any]](Set(1, 4)), Set(region))
          val grid = List(squareOther, squareTuple1, squareTuple2, squareTuple3)
          val reducedGrid = Solvers.reduceTuples(grid)
          assert(!reducedGrid.head.value.right.get.contains(1))
        }
      }
    }
  }
}
