import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable

class FizzBuzz extends Actor {

  override def receive: Receive = {
    case number: Int =>
      val originalSender = sender
      context.actorOf(Props(new Aggregator(originalSender, number)))
    case _ => println("unhandled message")
  }
}

private class Aggregator(client: ActorRef, number: Int) extends Actor {

  val fizzWorker = context.actorOf(Props(new MessageWorker(3, "Fizz")))
  val buzzWorker = context.actorOf(Props(new MessageWorker(5, "Buzz")))
  val bangWorker = context.actorOf(Props(new MessageWorker(7, "Bang")))
  val echoWorker = context.actorOf(Props(new EchoWorker()))

  val orderedMessages: mutable.Map[ActorRef, Option[String]] = mutable.Map(
    fizzWorker -> None,
    buzzWorker -> None,
    bangWorker -> None,
    echoWorker -> None)

  def isCompleted: Boolean = orderedMessages.forall {
    case (_, message) => message.isDefined
  }

  def assembleMessage(): String = {
    val fizz = orderedMessages(fizzWorker).get
    val buzz = orderedMessages(buzzWorker).get
    val bang = orderedMessages(bangWorker).get

    (fizz, buzz, bang) match {
      case ("", "", "") => orderedMessages(echoWorker).get

      case _ => s"$fizz$buzz$bang"
    }
  }

  override def receive: Receive = {
    case message: String =>
      orderedMessages(sender) = Some(message)
      if (isCompleted) {
        client ! assembleMessage()
        context.stop(self)
      }

    case _ => println("unhandled message")
  }

  fizzWorker ! number
  echoWorker ! number
  bangWorker ! number
  buzzWorker ! number
}