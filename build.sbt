name := """kingwilliams--slackbot"""

version := "1.0"

scalaVersion := "2.12.4"

scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/README.txt")

libraryDependencies ++= Seq(
  "org.specs2" % "specs2-core_2.12" % "3.8.9" % Test
  , "com.github.gilbertw1" %% "slack-scala-client" % "0.2.2"
  , "org.seleniumhq.selenium" % "selenium-java" % "2.52.0"

)
