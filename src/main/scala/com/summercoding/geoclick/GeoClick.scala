package com.summercoding.geoclick

import com.gargoylesoftware.htmlunit.html.{DomAttr, HtmlPage}
import grizzled.slf4j.Logging

import scala.collection.JavaConversions._

class GeoClick(val name: String) extends WebClientFactory with Logging {
  val allTripsUrl = s"http://$name.geoblog.pl/podroze"

  def clickFiveStarsForAllTrips() {
    info(s"Opening geoblog page: $allTripsUrl")

    newWebClient().getPage(allTripsUrl).asInstanceOf[HtmlPage]
      .getByXPath("//div[@class='userJournal']/a/@href").map {
      case (e: DomAttr) => e.getValue
    }.foreach(clickFiveStars)
  }

  def clickFiveStars(url: String): Unit = {
    debug(s"sending $url to actor")
    (new GeoClickActor).start() ! url
  }
}
