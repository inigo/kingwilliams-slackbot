package net.surguy.kingwilliams

import net.surguy.kingwilliams.publisher.QuestionPublisher
import net.surguy.kingwilliams.scraper.QuestionScraper

/**
  * Download the current King Williams College quiz questions from the Guardian, and
  * create Slack channels for them.
  */
class SlackInitializer(scraper: QuestionScraper, questionPublisher: QuestionPublisher) {

  def initialize() = {
    questionPublisher.publishQuestions(scraper.retrieveCategories())
  }

}


object SlackInitializer {
  import com.typesafe.config.ConfigFactory
  private val config = ConfigFactory.load()
  val token = config.getString("slack.token")
}

