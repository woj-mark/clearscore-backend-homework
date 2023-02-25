package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._

object csCardsRequest {

  case class CsCardsRequest(
      name: String,
      creditScore: Int
  )

  implicit
  def csCardsRequestEntityEncoder[F[_]]: EntityEncoder[F, CsCardsRequest] =
    jsonEncoderOf

}
