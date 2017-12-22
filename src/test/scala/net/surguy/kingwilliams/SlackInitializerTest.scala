package net.surguy.kingwilliams

import net.surguy.kingwilliams.SlackInitializer.token
import net.surguy.kingwilliams.publisher.QuestionPublisher
import net.surguy.kingwilliams.scraper.{QuestionScraper, QuizUrls}
import org.specs2.mutable.Specification

class SlackInitializerTest extends Specification {

  private val scraper = new QuestionScraper(QuizUrls.getUrl(2017).get, 2017)
  private val questionPublisher = new QuestionPublisher(token)

  val initializer = new SlackInitializer(scraper, questionPublisher)

  "Initializing Slack with questions" should {
    "do something sensible" in {
      initializer.initialize()
      ok
    }
  }

}
