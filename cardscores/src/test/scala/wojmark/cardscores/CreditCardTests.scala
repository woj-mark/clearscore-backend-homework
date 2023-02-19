package wojmark.cardscores

// import cats.effect.IO
// import org.http4s._
// import org.http4s.implicits._
import munit.CatsEffectSuite

/** * Testing if the /creditcards endpoint exposed returns correct responses **
  */
class CreditCardsTests extends CatsEffectSuite {

  test(
    "creditcards should return status code 200 for valid request from example"
  ) {
    // send {
    // "name": "Ada Lovelace",
    // "creditScore": 341,
    // "salary": 28000}
    // assertIO(retHelloWorld.map(_.status) ,Status.Ok)
  }

  test(
    "creditcards should return response as specified in the task specification on github"
  ) {
    // send {
    // "name": "Ada Lovelace",
    // "creditScore": 341,
    // "salary": 28000}

    // Response:
    //   [
    // {
    //   "provider": "ScoredCards"
    //   "name": "ScoredCard Builder",
    //   "apr": 19.4,
    //   "cardScore": 0.212
    // },
    // {
    //   "provider": "CSCards",
    //   "name": "SuperSaver Card",
    //   "apr": 21.4,
    //   "cardScore": 0.137
    // },
    // {
    //   "provider": "CSCards",
    //   "name": "SuperSpender Card",
    //   "apr": 19.2,
    //   "cardScore": 0.135
    // }

    // assertIO(retHelloWorld.map(_.status) ,Status.Ok)
  }

  test(
    "creditcards should return 400 Bad Request response when negative salary provided"
  ) {
    // assertIO(retHelloWorld.flatMap(_.as[String]), "{\"message\":\"Hello, world\"}")
  }

  test(
    "creditcards should return 400 Bad Request response when negative credit score provided"
  ) {
    // assertIO(retHelloWorld.flatMap(_.as[String]), "{\"message\":\"Hello, world\"}")
  }

  test(
    "creditcards should return 400 Bad Request response when negative credit score > 700 provided (max 700)"
  ) {
    // assertIO(retHelloWorld.flatMap(_.as[String]), "{\"message\":\"Hello, world\"}")
  }

  // private[this] val retCreditCards: IO[Response[IO]] = {
  // Implement a client to post the requests to the endpoint

  // val getCC = Request[IO](Method.POST, uri"/creditcards")
  // val helloWorld = HelloWorld.impl[IO]
  // CardscoresRoutes.helloWorldRoutes(helloWorld).orNotFound(getHW)
  // }
}
