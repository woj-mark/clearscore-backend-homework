package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.api.Refined
import io.circe.refined._ 
//import eu.timepit.refined.types.string.NonEmptyString
import eu.timepit.refined.collection.NonEmpty


//import io.circe._
//import io.circe.generic.semiauto._
import eu.timepit.refined.numeric.NonNegative

object scoredCardsRequest {

  case class ScoredCardsRequest(
      name: String Refined NonEmpty,
      score: Int,
      salary: Int Refined NonNegative
  )

  implicit def scoredCardsRequestEntityEncoder[F[_]]
      : EntityEncoder[F, ScoredCardsRequest] =
    jsonEncoderOf
}
