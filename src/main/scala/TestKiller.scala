object TestKiller {

  /**
    * Get all the combinations of distinct values between 1 and 9 whose sum is n
    *
    * @param n The expected sum
    */
  def getCombos(n: Int): List[List[Int]] = {
    case class Tree(value: Int, subTrees: List[Tree])
    def getCombosImpl(n: Int, candidates: List[Int]): Option[List[Tree]] = {
      if(n <= 0) Some(List.empty)
      else if(candidates.isEmpty || candidates.min > n) None
      else Some(candidates.filter(_ <= n).foldLeft(List.empty[Tree])((list, candidate) => {
        getCombosImpl(n - candidate, candidates.filterNot(_ <= candidate)) match {
          case Some(trees) => list :+ Tree(candidate, trees)
          case None => list
        }
      }))
    }
    def deepSearch(tree: Tree): List[List[Int]] = {
      if(tree.subTrees.isEmpty) List(List(tree.value))
      else tree.subTrees.flatMap(deepSearch).map(list => tree.value +: list)
    }

    getCombosImpl(n, (1 to 9).toList) match {
      case None => List.empty
      case Some(trees) => trees.flatMap(deepSearch).filter(_.sum == n)
    }
  }


}