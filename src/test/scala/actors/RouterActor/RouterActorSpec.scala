package actors.RouterActor

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{Matchers, WordSpec}
import scala.concurrent.Await
import scala.concurrent.duration._

class RouterActorSpec extends WordSpec with Matchers {
	val actorSystem = ActorSystem("ChildActorTest")
	var actor1 = actorSystem.actorOf(Props[Actor1])
	implicit val timeout = Timeout(2.seconds)

	"actors/RouterActor" should {
		"make possible to configure with what actor is speaking with by passing an ActorRef" in {
			var routerActor = actorSystem.actorOf(Props(new RouterActor(actor1)))
			val responseFuture = routerActor ? "speakingWith"

			val response = Await.result(responseFuture, timeout.duration)

			response should be("I'm Actor1")
		}
	}
}
