package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor
import akka.actor.ActorRef

class Worker(val canIWork: (Int) => Boolean, val job: (Int, String) => Any, val nextWork: ActorRef) extends Actor {

  def receive = {
    case Request(client, number, message) =>
      (canIWork(number), nextWork !=  null)  match {
        case (true, true) => nextWork ! Request(client, number, job(number, message).toString) 
        case (true, false) => client ! job(number, message)
        case (false, true) => nextWork ! Request(client, number, message)
        case (false, false) => client ! message
      }        
  }

}

case class Request(val client: ActorRef, val number: Int, val message: String = "")