package com.summercoding.geoclick

import com.gargoylesoftware.htmlunit.html.{HtmlImage, HtmlPage}
import grizzled.slf4j.Logging

import scala.actors.Actor

class GeoClickActor extends Actor with WebClientFactory with Logging {

  def act() {
    react {
      case url: String => clickFiveStars(url)
    }
  }

  def clickFiveStars(url: String) {
    info(s"Clicking five stars: $url")

    newWebClient().getPage(url).asInstanceOf[HtmlPage]
      .getFirstByXPath("//img[@id='star_5']").asInstanceOf[HtmlImage] match {
      case null => info(s"Five stars already clicked from this IP: $url")
      case img: HtmlImage => img.click(); info(s"Clicked five stars: $url")
    }

    Thread.sleep(1000)
  }
}
