package net.surguy.kingwilliams.scorer

import net.surguy.kingwilliams.SlackInitializer
import org.specs2.mutable.Specification

class ScorePublisherTest extends Specification {

  val counter = new Counter(SlackInitializer.token, 2017)
  val publisher = new ScorePublisher(SlackInitializer.token, "general", counter)

  "publishing scores" should {
    "write out scores to Slack" in {
      publisher.publishScores() must not(throwAn[Exception])
    }
  }

}
