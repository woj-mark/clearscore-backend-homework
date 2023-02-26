package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.collection.NonEmpty
import io.circe.refined._ 
import wojmark.cardscores.types.types.ZeroToSevenHundred
import eu.timepit.refined.api.Refined

object csCardsRequest {

/**
 * Data model for CardSearchRequest from Swagger definition of the CSCards microservice.
 * Refined library used to provide compile-time validation of the inputs in accordance with the
 * rules defined in Swagger definition for the CSCards microservice
 */

  case class CsCardsRequest(
      name: String Refined NonEmpty,
      creditScore: Int Refined ZeroToSevenHundred
  )


  //'jsonEncoderOf' constructs an EntityEncoder that can encode an instance of a CsCardsRequest case class to 
  // a JSON-encoded entity which can be sent in an HTTP response.
  // The Entity Encoder helps with the streaming nature of the way how http4s
  // handles requests (Request[F]) and responses (Responses[F]) in a streaming fashion
  implicit
  def csCardsRequestEntityEncoder[F[_]]: EntityEncoder[F, CsCardsRequest] =
    jsonEncoderOf

}
