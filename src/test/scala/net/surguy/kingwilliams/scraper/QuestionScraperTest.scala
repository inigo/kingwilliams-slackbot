package net.surguy.kingwilliams.scraper

import java.io.File
import java.nio.charset.StandardCharsets

import org.specs2.mutable.Specification

import scala.io.Source

class QuestionScraperTest extends Specification {

  private def doScrape(fileName: String) = {
    val localUrl = new File("src/test/resources/webpages/"+fileName).toURI
    new QuestionScraper(localUrl, 2016).retrieveCategories()
  }
  
  private lazy val cats2016 = doScrape("quiz2016.html")

  "Parsing categories for 2016" should {
    "identify the correct number of categories" in { cats2016 must haveLength(18) }
    "contain sensible category names" in { cats2016.head.preface mustEqual "In the year 1916:" }
    "use an empty string when the category is empty" in { cats2016(1).preface mustEqual "" }
  }

  "Identifying top level categories" should {
    "match the categories expected" in {
      cats2016.map(_.preface) mustEqual
        List("In the year 1916:", "", "", "", "In which encounter(s):", "", "", "Locate Ellan Vannin:",
          "", "", "", "", "", "Which library, where:", "", "", "", "During 2016:")
    }
    "be numbered from 1 to 18" in { cats2016.map(_.number) mustEqual (1 to 18) }
  }

  "Identifying questions" should {
    "return 10 questions in each category" in { cats2016.map(_.questions.length) must contain(beEqualTo(10)).forall  }
    "return sensible questions" in {
      cats2016.head.questions.map(_.text).take(2) mustEqual
        List("who won 277-254?", "which aristocrat fell prey to an eagle?")
    }
  }

  "retrieving content from the Guardian site" should {
    "return the expected number of categories" in {
      new QuestionScraper(QuizUrls.getUrl(2016).get, 2016).retrieveCategories() must haveLength(18)
    }
  }



  private def getWebpage(name: String): String = {
    Source.fromInputStream(this.getClass.getResourceAsStream("/webpages/"+name), StandardCharsets.UTF_8.name()).mkString
  }
}
