package net.surguy.kingwilliams.scraper

import org.specs2.mutable.Specification

class SplitterTest extends Specification {

  "Splitting lists based on an isEven predicate" should {
    "return an empty list for an empty list" in { split() mustEqual List(List()) }
    "return a single list for a single item matching the predicate" in { split(2) mustEqual List(List(2)) }
    "return a single list for a single item not matching the predicate" in { split(1) mustEqual List(List(1)) }
    "return a split list" in { split(2, 1, 4, 3) mustEqual List(List(2, 1), List(4, 3)) }
    "include the first items even when they do not match the predicate" in { split(5, 1, 4, 3) mustEqual List(List(5, 1), List(4, 3)) }
    "return a single list for multiple items that do not match" in { split(5, 1, 3, 9) mustEqual List(List(5, 1, 3, 9)) }
    "return multiple lists if all items match the predicate" in { split(2, 4, 6, 8) mustEqual List(List(2), List(4), List(6), List(8)) }
  }

  "Splitting using a different predicate" should {
    "respect that predicate" in { Splitter.splitOn(List(3, 5, 15, 3, 21))(_ > 10) mustEqual List(List(3, 5), List(15, 3), List(21))}
  }

  "Splitting a different type of contents" should {
    "work for strings similarly to for numbers" in {
      Splitter.splitOn(List("cat", "dog", "hat", "bat", "cow"))(_.contains("a")) mustEqual List(List("cat", "dog"), List("hat"), List("bat", "cow"))
    }
  }

  "Splitting an iterable" should {
    "return a split list" in { Splitter.splitOn(List(2, 1, 4, 3).toIterable)(_ % 2 == 0) mustEqual List(List(2, 1), List(4, 3)) }
  }

  private def split(items: Int*) = Splitter.splitOn(items)(_ % 2 == 0)

}
