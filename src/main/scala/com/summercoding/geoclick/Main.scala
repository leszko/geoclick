package com.summercoding.geoclick

import grizzled.slf4j.Logging

object Main extends Logging {
  def main(args: Array[String]) {
    args match {
      case Array(login) => new GeoClick(login).clickFiveStarsForAllTrips()
      case _ => error("Wrong execution arguments: " + args.deep.mkString(",")); info("Correct execution: ./geoclick <login>")
    }
  }
}
