package dao

import model.Square

trait GridDAO {

  def load(): List[Square]

}
