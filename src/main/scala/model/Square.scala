package model

case class Square(value: Either[Any, Set[Any]], regions: Set[Region]){

  def candidates: Set[Any] = value match {
    case Left(_) => Set.empty
    case Right(r) => r
  }

  def solutionFound(solution: Any): Square = Square(Left(solution), regions)
  def valueUpdated(newValue: Either[Any, Set[Any]]): Square = Square(newValue, regions)
  def isSolved: Boolean = value.isLeft

}

object Square {

  def apply(allValues: Set[Any], regions: Set[Region]): Square = new Square(Right(allValues), regions)

}

