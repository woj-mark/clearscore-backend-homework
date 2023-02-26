package wojmark.cardscores.domain

// import cats.implicits._
// import eu.timepit.refined.cats.syntax._
// import eu.timepit.refined.refineV
import eu.timepit.refined.api.Refined
//import eu.timepit.refined.boolean.Or
//import eu.timepit.refined.string.{IPv4, IPv6}
import eu.timepit.refined.numeric.NonNegative
//import eu.timepit.refined.collection.{ NonEmpty}
import io.circe.refined._ 
import io.circe._
import io.circe.generic.semiauto._
import eu.timepit.refined.collection.NonEmpty

//import eu.timepit.refined.types.numeric.NonNegInt
//import eu.timepit.refined.types.string.NonEmptyString

//As defined in the Swagger API documentation
//Currently using Scala basic types for name, credit score and salary
//TO DO: use Refined to contrain the types for the parameters

object cardsRequest {

  case class CardsRequest(
       name: String Refined NonEmpty,
      creditScore: Int,
      salary: Int Refined NonNegative
  )

      implicit val encoder: Encoder[CardsRequest] =  deriveEncoder[CardsRequest]
    implicit val decoder: Decoder[CardsRequest] =  deriveDecoder[CardsRequest]
}
