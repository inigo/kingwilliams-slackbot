package net.surguy.kingwilliams.scraper

import java.net.URI

/**
  * Quiz URLs on the Guardian site for the last few years.
  */
object QuizUrls {

  private val urls = Map(
    2013 -> "https://www.theguardian.com/theguardian/2013/dec/24/king-williams-college-quiz-2013"
    , 2015 -> "https://www.theguardian.com/theguardian/2015/dec/24/king-williams-college-quiz-2015"
    , 2016 -> "https://www.theguardian.com/theguardian/2016/dec/22/king-williams-college-quiz-2016"
    , 2017 -> "https://www.theguardian.com/theguardian/2017/dec/21/king-williams-college-quiz-2017"
    , 2023 -> "https://www.theguardian.com/theguardian/2023/dec/19/the-king-williams-college-quiz-2023"
  )

  def getUrl(year: Int): Option[URI] = urls.get(year).map(u => new URI(u))
}
