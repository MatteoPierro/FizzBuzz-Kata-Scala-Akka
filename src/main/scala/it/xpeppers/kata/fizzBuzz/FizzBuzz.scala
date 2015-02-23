package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import akka.actor.ActorRef

class FizzBuzz extends Actor {

  val fizzWorkerRouter = initializeRouter((number: Int) => "Fizz", 5)
  val buzzWorkerRouter = initializeRouter((number: Int) => "Buzz", 5)
  val fizzBuzzWorkerRouter = initializeRouter((number: Int) => "FizzBuzz", 2)
  val echoWorkerRouter = initializeRouter((number: Int) => number, 10)

  def receive = {
    case number: Int =>
      (number % 3 == 0, number % 5 == 0) match {
        case (true, false) => fizzWorkerRouter ! new Request(sender, number)
        case (false, true) => buzzWorkerRouter ! new Request(sender, number)
        case (true, true) => fizzBuzzWorkerRouter ! new Request(sender, number)
        case _ => echoWorkerRouter ! new Request(sender, number)
      }
  }

  private def initializeRouter(job: (Int) => Any, workerInstances: Int): ActorRef = {
    context.actorOf(Props(new Worker(job)).withRouter(RoundRobinRouter(workerInstances)))
  }
}