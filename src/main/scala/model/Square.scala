package model

case class Square(value: Either[Any, Set[Any]], regions: Set[Region]){

  def candidates: Set[Any] = value match {
    case Left(_) => Set.empty
    case Right(r) => r
  }

  def values: Set[Any] = value match {
    case Left(sol) => Set(sol)
    case Right(candidates) => candidates
  }

  def solutionFound(solution: Any): Square = Square(Left(solution), regions)
  def valueUpdated(newValue: Either[Any, Set[Any]]): Square = Square(newValue, regions)
  def regionsUpdated(newRegions: Set[Region]): Square = Square(value, newRegions)
  def isSolved: Boolean = value.isLeft

}

object Square {

  def apply(value: Either[Any, Set[Any]]): Square = new Square(value, Set.empty[Region])
  def apply(allValues: Set[Any], regions: Set[Region]): Square = new Square(Right(allValues), regions)
  def apply(allValues: Set[Any]): Square = new Square(Right(allValues), Set.empty[Region])

}

