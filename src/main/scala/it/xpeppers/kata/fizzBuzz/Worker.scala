package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor
import akka.actor.ActorRef

class Worker(val getMessage: (Int) => Any) extends Actor {

  def receive = {
    case Request(client, number) => client ! getMessage(number)
  }

}

case class Request(val client: ActorRef, val number: Int)