package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor

class FizzBuzz extends Actor {

  def receive = {
    case _ => sender ! "Fizz"
  }
}