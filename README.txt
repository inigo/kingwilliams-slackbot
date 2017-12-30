A bot for posting King Williams College quiz questions to Slack.

## Purpose

This will scrape the King William's College quiz questions from the Guardian, and post them to a Slack workspace,
so a group of people can collaborate on answering them.

The 2017 quiz questions are at: https://www.theguardian.com/theguardian/2017/dec/21/king-williams-college-quiz-2017

## Usage

This is a Scala/SBT project. You will need a Java virtual machine installed, and you need to have set up a Slack workspace,
and got a token to use a bot with it via https://my.slack.com/services/new/bot.

To set up, create a local.conf that specifies "slack.token", using the token from https://my.slack.com/services/new/bot.

You cannot currently create channels from a bot, so you need to create the channels yourself first, following the naming convention
in QuestionPublisher (e.g. 2017_01, 2017_02, 2017_03, etc.) and invite the bot to them.

Then configure and run the SlackInitializerTest. This will scrape the Guardian website, and add each question as a separate comment
in the appropriate channel. The bot sets the topic of each channel as it does it, and uses that topic name to check that it hasn't
already been run.

Do this via:

    bin/sbt
    > compile
    > test-only *SlackInitializerTest

Running ScorePublisherTest will check the questions to see if they have ticks against them (the :white_check_mark: emoji), and
count the number of ticks in each channel.

## License

The code is copyright (C) 2017 Inigo Surguy. The questions themselves are presumably either copyright King William's College,
or Guardian News and Media Limited.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this code except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.