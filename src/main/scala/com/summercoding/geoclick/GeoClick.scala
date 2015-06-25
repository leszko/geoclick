package com.summercoding.geoclick

import akka.actor.{ActorSystem, PoisonPill, Props}
import com.gargoylesoftware.htmlunit.html.{DomAttr, HtmlPage}
import com.summercoding.geoclick.ShutdownActor.WatchMe
import grizzled.slf4j.Logging

import scala.collection.JavaConversions._

class GeoClick(val name: String) extends WebClientFactory with Logging {
  val allTripsUrl = s"http://$name.geoblog.pl/podroze"

  val system = ActorSystem("GeoClickSystem")
  val shutdown = system.actorOf(Props[ShutdownActor])

  def clickFiveStarsForAllTrips() {
    info(s"Opening page: $allTripsUrl")

    newWebClient()
      .getPage(allTripsUrl)
      .asInstanceOf[HtmlPage]
      .getByXPath("//div[@class='userJournal']/a/@href")
      .map { case (e: DomAttr) => e.getValue }
      .foreach(clickFiveStars)
  }

  def clickFiveStars(url: String) {
    debug(s"sending $url to actor")

    val geoclick = system.actorOf(Props[GeoClickActor])
    shutdown ! WatchMe(geoclick)
    geoclick ! url
    geoclick ! PoisonPill
  }
}
