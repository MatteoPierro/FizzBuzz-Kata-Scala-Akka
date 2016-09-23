import akka.actor.Actor

class EchoWorker extends Actor{

  override def receive: Receive = {
    case number: Int => sender ! number.toString
    case _ => sender ! ""
  }
}
