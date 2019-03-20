package dao
import model.{Region, Square}

import scala.io.Source

class SpecialRegionLoaderCSV(val gridPath: String, val modelPath: String) extends GridDAO {

  override def load(): List[Square] = {
    val regionsToSquareIndices = Source.fromFile(modelPath).getLines().map(line => {
      (new Region, line.split(',').map(_.trim.toInt).toList)
    }).toMap
    Source.fromFile(gridPath).getLines().flatMap(
      _.split(',').map(_.trim.toInt match {
        case 0 => Square(Right[Any, Set[Any]]((1 to 9).toSet))
        case i => Square(Left(i))
      })
    ).toList.zipWithIndex.map(squareAndIndex => {
      squareAndIndex._1.regionsUpdated(regionsToSquareIndices.filter(_._2.contains(squareAndIndex._2 + 1)).keySet)
    })
  }

}
