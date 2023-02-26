package wojmark.cardscores.domain


import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.NonNegative
import io.circe.refined._ 
import io.circe._
import io.circe.generic.semiauto._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.auto._
import wojmark.cardscores.types.types.ZeroToSevenHundred



object cardsRequest {

/**
 * Data model for CreditCardRequest from Swagger definition of the /creditCards microservice.
 * Refined library used to provide compile-time validation of the inputs in accordance with the
 * rules defined in Swagger definition for partner's microservices
 */

  case class CardsRequest(
       name: String Refined NonEmpty,
      creditScore: Int Refined ZeroToSevenHundred,
      salary: Int Refined NonNegative
  )

  // Encoding and decoding rules for the CardsRequest (with circe)
    implicit val encoder: Encoder[CardsRequest] =  deriveEncoder[CardsRequest]
    implicit val decoder: Decoder[CardsRequest] =  deriveDecoder[CardsRequest]
}
