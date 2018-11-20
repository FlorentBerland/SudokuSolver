package model

import scala.annotation.tailrec

case class Grid(squares: Set[Square]){

  def getRegions: Set[Region] = squares.foldLeft(Set.empty[Region])((set, square) => set ++ square.regions)

  def getSquares(region: Region): Set[Square] =
    squares.foldLeft(Set.empty[Square])((set, square) => if(square.regions.contains(region)) set + square else set)

  /*
    Returns a set of values of squares solved in the given region
   */
  def getValues(region: Region): Set[Int] =
    getSquares(region).foldLeft(Set.empty[Int])((set, square) => square.value match {
      case Some(either) => either match {
        case Left(value) => set + value
        case _ => set
      }
      case _ => set
    })

  def isComplete: Boolean = squares.forall(_.value.getOrElse(Right()).isLeft)

  def hasTrivialSolution: Boolean = squares.forall(s => s.isSolved || s.candidates.nonEmpty)

  override def equals(obj: Any): Boolean = {
    if(!obj.isInstanceOf[Grid]) false
    else {
      // Squares coordinates and values matches
      @tailrec
      def checkSquaresTR(set1: Set[Square], set2: Set[Square]): Boolean = {
        if(set2.isEmpty) set1.isEmpty
        else set1.find(s => s.coordinates.equals(set2.head.coordinates) && s.value.isEmpty == set2.head.value.isEmpty) match {
          case Some(square) => checkSquaresTR(set1 - square, set2.tail)
          case None => false
        }
      }
    }
    false
  }

}