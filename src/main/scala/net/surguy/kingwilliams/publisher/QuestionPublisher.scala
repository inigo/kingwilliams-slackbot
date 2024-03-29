package net.surguy.kingwilliams.publisher

import akka.actor.ActorSystem
import net.surguy.kingwilliams.{Category, Question}
import slack.api.BlockingSlackApiClient
import slack.models.Channel

/**
  * Create a set of channels and questions in Slack.
  */
class QuestionPublisher(token: String) {

  private val client = BlockingSlackApiClient(token)
  private implicit val system: ActorSystem = ActorSystem("slack")

  def publishQuestions(categories: Seq[Category]): Unit = {
    for (cat <- categories) {
      val channelOpt = createCategoryChannel(cat)
      channelOpt match {
        case None => println("Ignoring category "+cat.number+" - either already set up, or no channel at all")
        case Some(channel) => cat.questions.foreach(q => createQuestion(channel, cat, q))
      }
    }
  }

  private[publisher] def createCategoryChannel(cat: Category): Option[Channel] = {
    val channelName = toChannelName(cat)
    println("Finding channel with name "+channelName) // Cannot create channels as a bot! Needs to already exist
    val channelOpt = client.listChannels().find(_.name.getOrElse("") == channelName)
    channelOpt match {
      case Some(channel) =>
        val topic = (cat.number + " " + cat.preface).trim
        println("Setting channel topic to "+topic)
        channel.topic match {
          case Some(v) if v.value==topic =>
            println("Channel topic already correct")
            None
          case _ =>
            // @todo Setting channel topic doesn't appear to work any more - getting an "unknown method" back from Slack
//            client.setChannelTopic(channel.id, topic)
            client.postChatMessage(channel.id, s"-----\n*Section ${cat.number})* ${cat.preface}\n-----", username = Some("kingwilliamsquiz"))
            Some(channel)
        }
      case None =>
        println("Could not find an appropriate channel")
        None
    }
  }

  private[publisher] def createQuestion(channel: Channel, cat: Category, q: Question): Unit = {
    println("Posting question: "+q.text)
    client.postChatMessage(channel.id, s"${q.number}) ${cat.preface} *${q.text}*", username = Some("kingwilliamsquiz")) // * bolds the text
  }

  private[publisher] def toChannelName(cat: Category) = f"${cat.year}_${cat.number}%02d"

}
