package wojmark.cardscores

import com.comcast.ip4s.{Ipv4Address, Port}
import org.http4s.Uri
import org.http4s._

import org.http4s.implicits._

object Config {

  def getPort(portVariableName: String): Port = {
    Port.fromString(sys.env.getOrElse(portVariableName, "8080")).get
  }

  def getHost(hostVariableName: String): Ipv4Address = {
    (Ipv4Address
      .fromString(
        (sys.env.getOrElse(hostVariableName, "0.0.0.0"))
      ))
      .get
  }

  def getUriByServiceName(serviceName: String): Uri = {
    serviceName match {
      case "CSCARDS_ENDPOINT"     => Config.getCSCardsUri()
      case "SCOREDCARDS_ENDPOINT" => Config.getScoredCardsUri()
    }
  }

  def getCSCardsUri(): Uri = {
    Uri
      .fromString(
        sys.env.getOrElse(
          "CSCARDS_ENDPOINT",
          "https://app.clearscore.com/api/global/backend-tech-test/v1/cards"
        )
      )
      //This is to be refactored (BAD PRACTICE)
      .getOrElse(
        uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards"
      )
  }

  def getScoredCardsUri(): Uri = {
    Uri
      .fromString(
        sys.env.getOrElse(
          "SCOREDCARDS_ENDPOINT",
          "https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
        )
      )
      //This is to be refactored (BAD PRACTICE)
      .getOrElse(
        uri"https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
      )
  }

  def getProductId(userIdVariableName: String): ProductId = {
    ProductId(
      sys.env
        .getOrElse(
          userIdVariableName,
          "wojmark-cardscores"
        )
    )
  }

}
