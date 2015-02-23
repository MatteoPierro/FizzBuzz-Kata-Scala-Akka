package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor

class FizzBuzz extends Actor {

  def receive = {
    case number : Int =>
      number match{
        case 3 => sender ! "Fizz"
        case _ => sender ! "Buzz"
      }
  }
}