package wojmark.cardscores.domain


import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.api.Refined
import io.circe.refined._ 
import eu.timepit.refined.collection.NonEmpty

import eu.timepit.refined.numeric.NonNegative
import wojmark.cardscores.types.types.ZeroToSevenHundred


object scoredCardsRequest {
//type ZertoToSevenHundred = Not[Less[0]] And Not[Greater[700]]


  case class ScoredCardsRequest(
      name: String Refined NonEmpty,
      score: Int Refined ZeroToSevenHundred,
      salary: Int Refined NonNegative
  )

  implicit def scoredCardsRequestEntityEncoder[F[_]]
      : EntityEncoder[F, ScoredCardsRequest] =
    jsonEncoderOf
}
