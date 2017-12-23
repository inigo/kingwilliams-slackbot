package net.surguy.kingwilliams.scorer

import net.surguy.kingwilliams.SlackInitializer
import net.surguy.kingwilliams.publisher.QuestionPublisher
import org.specs2.mutable.Specification

class CounterTest extends Specification {

  val counter = new Counter(SlackInitializer.token, 2017)

  "identifying channels" should {
    "find 18 relevant channels" in {
      counter.relevantChannels() must haveLength(18)
    }
  }

  "identifying messages" should {
    "find 10 relevant messages" in {
      counter.relevantMessages(counter.relevantChannels().head) must haveLength(10)
    }
  }

  "parsing Slack messages" should {
    "parse a bot message" in {
      val json = """{"text":"10) Which personality? *20-17, Sydney.*","username":"kingwilliamsquiz","bot_id":"B8FUQ1W90","type":"message","subtype":"bot_message","thread_ts":"1513880437.000242","reply_count":3,"replies":[{"user":"U8F4K2WKD","ts":"1513894785.000116"},{"user":"U8G8PJMSB","ts":"1513952818.000507"},{"user":"U8FAR0Q77","ts":"1513960007.000529"}],"subscribed":false,"unread_count":3,"ts":"1513880437.000242","reactions":[{"name":"white_check_mark","users":["U8FAR0Q77"],"count":1}]}"""
      counter.parseMessage(json).text mustEqual "10) Which personality? *20-17, Sydney.*"
      counter.parseMessage(json).isCorrect must beTrue
    }
  }

  "finding channel stats" should {
    "calculate stats" in {
      println(counter.channelStats())
      counter.channelStats() must haveLength(18)
    }
  }

}
