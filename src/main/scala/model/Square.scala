package model

class Square(val value: Either[Any, Set[Any]], val regions: Set[Region]){

  def candidates: Set[Any] = value match {
    case Left(_) => Set.empty
    case Right(r) => r
  }

  def solutionFound(solution: Any): Square = Square(Left(solution), regions)
  def valueUpdated(newValue: Either[Any, Set[Any]]): Square = Square(newValue, regions)
  def isSolved: Boolean = value.isLeft

}

object Square {

  def apply(value: Either[Any, Set[Any]], regions: Set[Region]): Square = new Square(value, regions)
  def apply(value: Either[Any, Set[Any]]): Square = new Square(value, Set.empty[Region])
  def apply(allValues: Set[Any], regions: Set[Region]): Square = new Square(Right(allValues), regions)
  def apply(allValues: Set[Any]): Square = new Square(Right(allValues), Set.empty[Region])

}

