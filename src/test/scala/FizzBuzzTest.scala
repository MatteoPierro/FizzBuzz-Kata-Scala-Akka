import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import akka.actor.ActorSystem
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import akka.actor.Props

class FizzBuzzTest extends TestKit(ActorSystem("FizzBuzzSystem")) with ImplicitSender
  with WordSpecLike with Matchers {

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

    "return Bang Message for multiples of the number 7" in {
      checkMessage(7, "Bang")
      checkMessage(14, "Bang")
    }

    "return the received number if it isn't multiple of 3, 5 or 7" in {
      checkMessage(1, "1")
      checkMessage(11, "11")
      checkMessage(47, "47")
    }

    "return FizzBuzz Message for multiples of 3 and 5" in {
      checkMessage(15, "FizzBuzz")
      checkMessage(30, "FizzBuzz")
      checkMessage(45, "FizzBuzz")
    }

    "return FizzBang Message for multiples of 3 and 7" in {
      checkMessage(21, "FizzBang")
      checkMessage(42, "FizzBang")
      checkMessage(63, "FizzBang")
    }

    "return BuzzBang Message for multiples of 5 and 7" in {
      checkMessage(35, "BuzzBang")
      checkMessage(70, "BuzzBang")
    }

    "return FizzBuzzBang Message for multiples of 3, 5 and 7" in {
      checkMessage(105, "FizzBuzzBang")
      checkMessage(210, "FizzBuzzBang")
    }
  }

  def checkMessage(number: Int, message: Any) {
    val fizzBuzz = system.actorOf(Props[FizzBuzz])
    fizzBuzz ! number
    expectMsg(message)
  }
}