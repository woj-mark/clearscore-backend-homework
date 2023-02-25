package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._

object scoredCardsRequest {

  case class ScoredCardsRequest(
      name: String,
      score: Int,
      salary: Int
  )

  implicit def scoredCardsRequestEntityEncoder[F[_]]
      : EntityEncoder[F, ScoredCardsRequest] =
    jsonEncoderOf
}
