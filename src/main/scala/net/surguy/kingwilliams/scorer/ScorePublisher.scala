package net.surguy.kingwilliams.scorer

import akka.actor.ActorSystem
import slack.api.BlockingSlackApiClient

/**
  * Publish scores and unanswered questions to a Slack channel.
  */
class ScorePublisher(token: String, publishToChannel: String, counter: Counter) {

  private val client = BlockingSlackApiClient(token)
  private implicit val system: ActorSystem = ActorSystem("slack")

  def publishScores() = {
    val channel = client.listChannels().find(_.name==publishToChannel).get
    val stats = counter.channelStats()
    val total = stats.map(_.score).sum
    val msg = stats.map(c => s"${c.name} : *${c.score}* correct answers").mkString("\n")+s"\n*Total: $total out of 180*"
    client.postChatMessage(channel.id, "Results are:\n"+msg)
  }

  def publishUnansweredQuestions() = {
    val channel = client.listChannels().find(_.name==publishToChannel).get
    val unanswered = counter.unansweredQuestions()
    val msg = unanswered.map{ case(c, m) =>
      val channelNo = c.topic.get.value.replaceAll("^(\\d+).*", "$1").toInt
      val questionNo = m.text.replaceAll("^(\\d+).*", "$1").toInt
      ((channelNo*100) + questionNo, s"Section $channelNo -- ${m.text}")
    }.sortBy(_._1).map(_._2).mkString("\n")
    client.postChatMessage(channel.id, s"There are ${unanswered.length} questions that have not yet been answered:\n"+msg)
  }

}
