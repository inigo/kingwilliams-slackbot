package net.surguy.kingwilliams.scorer

import akka.actor.ActorSystem
import slack.api.BlockingSlackApiClient

/**
  * Publish scores to a Slack channel.
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

}
