package net.surguy.kingwilliams.publisher

import net.surguy.kingwilliams.{Category, Question, SlackInitializer}
import org.specs2.mutable.Specification

class QuestionPublisherTest extends Specification {

  val publisher = new QuestionPublisher(SlackInitializer.token)

  "creating content in Slack" should {
    "not fail when doing nothing" in {
      publisher.publishQuestions(2000, Seq())
      ok
    }
    "create channels and questions" in {
      publisher.publishQuestions(2000, Seq(Category(1, 2000, "Who in 2000:", List(Question(1, "Ate some cheese?"), Question(2, "Liked pie?")))))
      ok
    }
  }

}
