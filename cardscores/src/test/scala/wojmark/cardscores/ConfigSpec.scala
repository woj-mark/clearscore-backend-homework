package wojmark.cardscores

import com.comcast.ip4s.{Port, Ipv4Address}
import org.http4s.Uri
import org.http4s.implicits._
import org.http4s._

//import wojmark.cardscores.domain.cardScore.CardScore

class ConfigLogicTests extends munit.FunSuite {

  test(
    "Test if getPort returns a Port:8080 if port not provided in env variables/ or not existing "
  ) {
    val testPort: Port = Port.fromString("8080").get
    // CAVEAT: Such env variable must not exist in the env
    val nonExistingEnvPortVar = "PORT-NON-EXIST"

    assert(
      Config.getPort(nonExistingEnvPortVar) == testPort,
      "Port address provided incorrectly"
    )
  }

  test(
    "Test if getHost returns a Host=0.0.0.0 if host not provided in env variables/ or not existing "
  ) {
    val testHost: Ipv4Address = Ipv4Address.fromString("0.0.0.0").get
    // CAVEAT: Such env variable must not exist in the env
    val nonExistingEnvPortVar = "HOST-NON-EXIST"

    assert(
      Config.getHost(nonExistingEnvPortVar) == testHost,
      "Host address provided incorrectly"
    )

  }

  test(
    "Test if getUriByServiceName returns the correct endpoint URIs to partner services"
  ) {
    val csCardsEnvName: String = "CSCARDS_ENDPOINT"
    val csCardsEndpoint: Uri =
      uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards"
    assert(
      Config.getUriByServiceName(csCardsEnvName) == csCardsEndpoint,
      "URI endpoint for csCards provided incorrectly"
    )

    val scoredCardsEnvName: String = "SCOREDCARDS_ENDPOINT"
    val scoredCardsEndpoint: Uri =
      uri"https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
    assert(
      Config.getUriByServiceName(scoredCardsEnvName) == scoredCardsEndpoint,
      "URI endpoint for ScoredCards provided incorrectly"
    )

  }

  test(
    "Test if getProductId returns a User-Agent ProductId if USER_AGENT_ID not provided in env variables/ or not existing "
  ) {
    val testProductId: ProductId = ProductId("wojmark-cardscores")
    // CAVEAT: Such env variable must not exist in the env
    val nonExistingUserAgentIdVar = "USER_AGENT_ID_DUMMY"

    assert(
      Config.getProductId(nonExistingUserAgentIdVar) == testProductId,
      "User-Agent ProductId address provided incorrectly"
    )

  }

}
