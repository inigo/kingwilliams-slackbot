package net.surguy.kingwilliams.scraper

import scala.collection.mutable.ListBuffer

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
    val splitLists = new ListBuffer[List[T]]()
    val currentList = new ListBuffer[T]()

    for (item <- items) {
      if (predicate(item) && currentList.nonEmpty) {
        splitLists += currentList.toList
        currentList.clear()
      }
      currentList += item
    }

    splitLists += currentList.toList
    splitLists.toList
  }

}
