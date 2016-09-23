import akka.actor.Actor

class MessageWorker(val number: Int, val message: String) extends Actor {

  override def receive = {
    case n: Int if (n % number) == 0 => sender ! message
    case _ => sender ! ""
  }
}