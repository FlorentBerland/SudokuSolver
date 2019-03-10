package dao
import model.{Region, Square}

class ClassicSudokuCSV(path: String) extends GridDAO {

  override def load(): List[Square] = {
    val gridWithoutRegions = io.Source.fromFile(path).getLines().flatMap(
      _.split(',').map(_.trim.toInt match {
        case 0 => Square(Right[Any, Set[Any]]((1 to 9).toSet))
        case i => Square(Left(i))
      })
    ).toList
    val rows = Array.fill(9)(new Region)
    val columns = Array.fill(9)(new Region)
    val blocks = Array.fill(3, 3)(new Region)
    (0 to 8).flatMap(i => (0 to 8).map(j => gridWithoutRegions(9*i + j).regionsUpdated(Set(rows(i), columns(j), blocks(i/3)(j/3))))).toList
  }

}
