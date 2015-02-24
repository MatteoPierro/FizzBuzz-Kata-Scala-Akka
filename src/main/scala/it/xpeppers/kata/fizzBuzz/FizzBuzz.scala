package it.xpeppers.kata.fizzBuzz

import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import akka.actor.ActorRef

class FizzBuzz extends Actor {

  val echoWorkerRouter = initializeRouter((number: Int) => (number % 3 != 0 && number % 5 != 0 && number % 7 != 0), (number: Int, string: String) => number)
  val bangWorkerRouter = initializeRouter((number: Int) => (number % 7 == 0), (number: Int, string: String) => (string + "Bang"), echoWorkerRouter)
  val buzzWorkerRouter = initializeRouter((number: Int) => (number % 5 == 0), (number: Int, string: String) => (string + "Buzz"), bangWorkerRouter)
  val fizzWorkerRouter = initializeRouter((number: Int) => (number % 3 == 0), (number: Int, string: String) => (string + "Fizz"), buzzWorkerRouter)

  def receive = {
    case number: Int => fizzWorkerRouter ! Request(sender, number)
  }

  private def initializeRouter(canIWork: (Int) => Boolean, job: (Int, String) => Any, nextWorker: ActorRef = null, workerInstances: Int = 10): ActorRef = {
    context.actorOf(Props(new Worker(canIWork, job, nextWorker)).withRouter(RoundRobinRouter(workerInstances)))
  }
}