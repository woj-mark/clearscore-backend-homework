package wojmark.cardscores.domain

import io.circe.generic.auto._
import cats.effect.Concurrent

import org.http4s._
import org.http4s.circe._

object scoredCardsResponse {

  case class ScoredCardsResponse(
      card: String,
      apr: Double,
      approvalRating: Double
  )

  implicit def scoredCardsResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, ScoredCardsResponse] =
    jsonOf

  implicit def scoredCardsListResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, List[ScoredCardsResponse]] =
    jsonOf

}
