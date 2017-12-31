package net.surguy.kingwilliams.scorer

import akka.actor.ActorSystem
import play.api.libs.json.{JsValue, Json}
import slack.api.BlockingSlackApiClient
import slack.models.Channel

/**
  * Work out the total number of questions answered, by checking for tick marks.
  */
class Counter(token: String, year: Int) {

  private val client = BlockingSlackApiClient(token)
  private implicit val system: ActorSystem = ActorSystem("slack")

  def channelStats(): Seq[ChannelStats] = {
     relevantChannels().map{ c =>
       val score = relevantMessages(c).map(m => if (m.isCorrect) 1 else 0).sum
       ChannelStats(c.name, score)
     }.sortBy(_.name)
  }

  def unansweredQuestions(): Seq[(Channel, Message)] = {
    relevantChannels().flatMap(c => relevantMessages(c).filterNot(_.isCorrect).map(m => (c, m)))
  }

  private[scorer] def relevantChannels(): Seq[Channel] = {
    client.listChannels().filter(_.name.startsWith(""+year+"_"))
  }

  private[scorer] def relevantMessages(c: Channel): Seq[Message] = {
    val s = client.getChannelHistory(channelId = c.id).messages
    client.getChannelHistory(channelId = c.id).messages.map(m => parseMessage(m.toString())).filter(_.text.matches("\\d+.*"))
  }

  private[scorer] def parseMessage(msg: String): Message = {
    val json = Json.parse(msg)
    val text = (json \ "text").asOpt[String].getOrElse("")
    val reactionNames: String = (json \ "reactions").toOption.map(_.toString()).getOrElse("")
    val isCorrect = reactionNames.contains("white_check_mark")
    Message(text, isCorrect)
  }

  case class ChannelStats(name: String, score: Int)
  case class Message(text: String, isCorrect: Boolean)

}
