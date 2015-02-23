package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor

class FizzBuzz extends Actor {

  def receive = {
    case number : Int =>
      (number % 3 == 0, number %5 == 0) match{
        case (true, false) => sender ! "Fizz"
        case (false, true) => sender ! "Buzz"
        case (true, true) => sender ! "FizzBuzz"
        case _=> sender ! number
      }
  }
}