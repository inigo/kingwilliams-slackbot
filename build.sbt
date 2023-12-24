name := """kingwilliams--slackbot"""

version := "1.0"

scalaVersion := "2.13.10"

Global / onChangedBuildSource := ReloadOnSourceChanges

Compile / doc / scalacOptions  ++= Seq("-doc-root-content", baseDirectory.value+"/README.txt")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "4.5.1" % Test
  , "com.github.slack-scala-client" %% "slack-scala-client" % "0.4.5"
  , "org.seleniumhq.selenium" % "selenium-java" % "2.52.0"

)
