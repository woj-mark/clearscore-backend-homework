package wojmark.cardscores.domain


import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.NonNegative
import io.circe.refined._ 
import io.circe._
import io.circe.generic.semiauto._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.auto._
import wojmark.cardscores.types.types.ZeroToSevenHundred

//As defined in the Swagger API documentation
//Currently using Scala basic types for name, credit score and salary
//TO DO: use Refined to contrain the types for the parameters

object cardsRequest {

  case class CardsRequest(
       name: String Refined NonEmpty,
      creditScore: Int Refined ZeroToSevenHundred,
      salary: Int Refined NonNegative
  )

      implicit val encoder: Encoder[CardsRequest] =  deriveEncoder[CardsRequest]
    implicit val decoder: Decoder[CardsRequest] =  deriveDecoder[CardsRequest]
}
