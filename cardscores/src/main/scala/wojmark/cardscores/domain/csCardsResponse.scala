package wojmark.cardscores.domain

import io.circe.generic.auto._
import cats.effect.Concurrent

import org.http4s._
import org.http4s.circe._

object csCardsResponse {

  case class CsCardsResponse(
      cardName: String,
      apr: Double,
      eligibility: Double
  )

  implicit def csCardsResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, CsCardsResponse] =
    jsonOf

  implicit def csCardsListResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, List[CsCardsResponse]] =
    jsonOf

}
