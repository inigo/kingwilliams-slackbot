package net.surguy.kingwilliams.scraper

import java.net.URI
import java.util.concurrent.TimeUnit

import com.gargoylesoftware.htmlunit.BrowserVersion
import net.surguy.kingwilliams.{Category, Question}
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.{By, WebDriver}

import scala.collection.JavaConverters._

/**
  * Read and parse questions from a web page.
  */
class QuestionScraper(questionsUrl: URI, year: Int) {
  private val browser = createHtmlUnitDriver(enableJavaScript = false)

  def retrieveCategories(): List[Category] = {
    browser.navigate().to(questionsUrl.toURL)
    val elements = asScalaBuffer(browser.findElements(By.cssSelector(".content__article-body *, .article-body-viewer-selector *")))
                      .filter(el =>el.getTagName=="h2" || el.getTagName=="p")

    val groups = Splitter.splitOn(elements)(_.getTagName=="h2")
    // Ignore the first group, because it is intro text
    for (group <- groups.drop(1).zipWithIndex) yield {
      val header = group._1.head
      val questions = group._1
                      .drop(1)
                      .map(_.getText).filter(_.matches("\\d+.*")) // The final section contains paragraphs that are not questions
                      .map(removeLeadingNumber) // We handle the number ourselves separately - it's inconsistently formatted across years
                      .zipWithIndex.map( q => Question(q._2 + 1, q._1) )
      Category(group._2 + 1, year, removeLeadingNumber(header.getText), questions)
    }
  }

  private def removeLeadingNumber(s: String) = s.replaceFirst("\\d+\\.?\\s*", "").trim

  private def createHtmlUnitDriver(enableJavaScript: Boolean): WebDriver = {
    val driver = new HtmlUnitDriver(BrowserVersion.CHROME)
    driver.setJavascriptEnabled(enableJavaScript)
    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
    driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS)
    driver
  }

}


