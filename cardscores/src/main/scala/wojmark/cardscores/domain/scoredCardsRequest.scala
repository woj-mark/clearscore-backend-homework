package wojmark.cardscores.domain


import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.api.Refined
import io.circe.refined._ 
import eu.timepit.refined.collection.NonEmpty

import eu.timepit.refined.numeric.NonNegative

//Importing the type alias for constraining the range of the credit score (0-700)
import wojmark.cardscores.types.types.ZeroToSevenHundred


object scoredCardsRequest {

/**
 * Data model for ScoredCardsRequest from Swagger definition of the ScoredCards microservice.
 * Refined library used to provide compile-time validation of the inputs in accordance with the
 * rules defined in Swagger definition for the CSCards microservice
 */

  case class ScoredCardsRequest(
      name: String Refined NonEmpty,
      score: Int Refined ZeroToSevenHundred,
      salary: Int Refined NonNegative
  )


  //'jsonEncoderOf' constructs an EntityEncoder that can encode an instance of a ScoredCardsRequest case class to 
  // a JSON-encoded entity which can be sent in an HTTP response.
  // The Entity Encoder helps with the streaming nature of the way how http4s
  // handles requests (Request[F]) and responses (Responses[F]) in a streaming fashion
  implicit def scoredCardsRequestEntityEncoder[F[_]]
      : EntityEncoder[F, ScoredCardsRequest] =
    jsonEncoderOf
}
