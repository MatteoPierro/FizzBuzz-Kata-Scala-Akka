package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor

class FizzBuzz extends Actor {

  def receive = {
    case number : Int =>
      (number % 3 == 0) match{
        case true => sender ! "Fizz"
        case _ => sender ! "Buzz"
      }
  }
}