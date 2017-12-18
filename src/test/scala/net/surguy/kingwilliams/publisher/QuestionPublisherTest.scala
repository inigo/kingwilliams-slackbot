package net.surguy.kingwilliams.publisher

import net.surguy.kingwilliams.{Category, Question, SlackInitializer}
import org.specs2.mutable.Specification

class QuestionPublisherTest extends Specification {

  val publisher = new QuestionPublisher(SlackInitializer.token)

  "creating content in Slack" should {
    "not fail when doing nothing" in {
      publisher.publishQuestions(Seq())
      ok
    }
    "create channels and questions" in {
      publisher.publishQuestions(Seq(Category(1, 2000, "Who in 2000:", List(Question(1, "Ate some cheese?"), Question(2, "Liked pie?")))))
      ok
    }
  }

  "channel names" should {
    "include the year and number" in { publisher.toChannelName(Category(3, 2000, "xxx", List())) mustEqual "2000_03" }
    "have the number right aligned" in { publisher.toChannelName(Category(11, 2000, "xxx", List())) mustEqual "2000_11" }
  }

}
