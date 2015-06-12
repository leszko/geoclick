package com.summercoding.geoclick

import com.gargoylesoftware.htmlunit.WebClient

trait WebClientFactory {
  def newWebClientWithNoExceptions(): WebClient = {
    val webClient = new WebClient()
    webClient.getOptions.setThrowExceptionOnFailingStatusCode(false)
    webClient.getOptions.setThrowExceptionOnScriptError(false)
    webClient.getOptions.setJavaScriptEnabled(true)
    return webClient
  }
}
