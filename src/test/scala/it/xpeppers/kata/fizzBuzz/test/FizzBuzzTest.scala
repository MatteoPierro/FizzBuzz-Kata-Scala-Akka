package it.xpeppers.kata.fizzBuzz.test

import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import org.scalatest.BeforeAndAfterAll
import akka.actor.ActorSystem
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import akka.actor.Props
import it.xpeppers.kata.fizzBuzz.FizzBuzz
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FizzBuzzTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("FizzBuzzSystem"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "FizzBuzz actor" must {

    "return Fizz message for the number three" in {
      checkMessage(3, "Fizz")
    }

    "return Buzz message for the number five" in {
      checkMessage(5, "Buzz")
    }
    
    "return Fizz Message for multiples of the number 3" in {
      checkMessage(6, "Fizz")
      checkMessage(9, "Fizz")
    }
    
    "return Buzz Message for multiples of the number 5" in {
      checkMessage(10, "Buzz")
      checkMessage(20, "Buzz")
    }
  }

  def checkMessage(number: Int, message: String) {
    val fizzBuzz = system.actorOf(Props[FizzBuzz])
    fizzBuzz ! number
    expectMsg(message)
  }

}