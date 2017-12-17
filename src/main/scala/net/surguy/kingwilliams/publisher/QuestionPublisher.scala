package net.surguy.kingwilliams.publisher

import akka.actor.ActorSystem
import net.surguy.kingwilliams.Category
import slack.api.BlockingSlackApiClient

/**
  * Create a set of channels and questions in Slack.
  */
class QuestionPublisher(token: String) {

  private val client = BlockingSlackApiClient(token)
  private implicit val system: ActorSystem = ActorSystem("slack")
  private val topic = "King Williams College quiz questions"

  def publishQuestions(year: Int, categories: Seq[Category]): Unit = {
    val channelName = "" + year
    val channelOpt = client.listChannels().find(_.name == channelName)
    channelOpt match {
      case None =>
        println(s"Could not find a channel with name '$channelName' - either doesn't exist, or the bot hasn't been invited to it")
      case Some(channel) if channel.topic.isDefined && channel.topic.get.value == topic =>
        println(s"Channel $channelName exists and already has the expected topic of $topic - ignoring it")
      case Some(channel) =>
        client.setChannelTopic(channel.id, topic)
        for (cat <- categories) {
          client.postChatMessage(channel.id, s"------\n*Section ${cat.number}) ${cat.preface}*\n-----", username = Some("kingwilliamsquiz"))

          for (q <- cat.questions) {
            client.postChatMessage(channel.id, s"${q.number}) ${cat.preface} *${q.text}*", username = Some("kingwilliamsquiz"))
          }
        }
    }
  }

}
