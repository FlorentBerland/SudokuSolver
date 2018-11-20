package model

import java.awt.Point

case class Square(coordinates: Point, value: Option[Either[Int, Set[Int]]], regions: Set[Region]){

  def candidates: Set[Int] = value match {
    case Some(either) => either match {
      case Left(_) => Set.empty
      case Right(r) => r
    }
    case None => Square.getValues
  }

  def valueFound(value: Int): Square = Square(coordinates, Some(Left(value)), regions)
  def candidatesSet(candidates: Set[Int]): Square = Square(coordinates, Some(Right(candidates)), regions)
  def valueChanged(newValue: Option[Either[Int, Set[Int]]]): Square = Square(coordinates, newValue, regions)
  def isSolved: Boolean = value.getOrElse(Right()).isLeft

  /*
    Tests whether the coordinates and the value matches, independently of the region
   */
  def matches(square: Square): Boolean = {
    if(!coordinates.equals(square.coordinates)) false
    value match {
      case Some(either) =>
        if(square.value.nonEmpty) false
        else either match {
          case Left(solution) =>
            false // FIXME
          case Right(candidates) =>
            false
        }
      case None => square.value.isEmpty
    }
  }

}

object Square {
  def getValues: Set[Int] = (1 to 9).toSet
}