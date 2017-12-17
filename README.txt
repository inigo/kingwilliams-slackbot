A bot for posting King Williams College quiz questions to Slack.

== Usage ==

This is a Scala/SBT project.

Build and test with:

{{{
    bin/sbt
    > compile
    > test
}}}

To use, create a local.conf that specifies "slack.token". The token you can get from https://my.slack.com/services/new/bot.

Then configure and run the SlackInitializerTest. You'll need to create the channel(s) first, following the naming convention
in QuestionPublisher, and invite the bot to those channels, because bots aren't allowed to create channels.