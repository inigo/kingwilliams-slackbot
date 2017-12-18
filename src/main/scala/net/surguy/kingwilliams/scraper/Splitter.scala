package net.surguy.kingwilliams.scraper

/**
  * Utility to support splitting lists into multiple lists based on a predicate, while maintaining ordering.
  */
object Splitter {

  /**
    * Split a list of items into N lists, for which each new list (except possibly the first) begins with an item
    * matching the predicate, maintaining order. For example, with an 'isEven' predicate:
    *  (3, 5, 2, 3, 4, 6, 1, 9) => (3, 5), (2, 3), (4), (6, 1, 9)
    *  (2, 1, 4, 5) => (2, 1), (4, 5)
    */
  def splitOn[T](items: Iterable[T])(predicate: (T => Boolean)): List[List[T]] = {
    val initial = (List[List[T]](), List[T]() )
    val split = items.foldLeft(initial){
      case ((allSplit, currentAccumulator), current) =>
        val isMatch = predicate(current)
        val newAllSplit = if (isMatch && currentAccumulator.nonEmpty) allSplit :+ currentAccumulator else allSplit
        val newAccumulator = if (isMatch) List(current) else currentAccumulator :+ current
        (newAllSplit, newAccumulator)
    }
    split._1 :+ split._2
  }

}
