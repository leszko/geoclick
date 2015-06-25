package com.summercoding.geoclick

import akka.actor.{Actor, ActorRef, Terminated}
import com.summercoding.geoclick.ShutdownActor.WatchMe

import scala.collection.mutable.ArrayBuffer

object ShutdownActor {

  case class WatchMe(ref: ActorRef)

}

class ShutdownActor extends Actor {
  val watched = ArrayBuffer.empty[ActorRef]

  final def receive = {

    case WatchMe(ref) =>
      context.watch(ref)
      watched += ref

    case Terminated(ref) =>
      watched -= ref
      if (watched.isEmpty) context.system.shutdown()
  }
}
