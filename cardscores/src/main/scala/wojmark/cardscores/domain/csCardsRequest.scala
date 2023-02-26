package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.collection.NonEmpty
import io.circe.refined._ 
import wojmark.cardscores.types.types.ZeroToSevenHundred
import eu.timepit.refined.api.Refined

object csCardsRequest {

  case class CsCardsRequest(
      name: String Refined NonEmpty,
      creditScore: Int Refined ZeroToSevenHundred
  )

  implicit
  def csCardsRequestEntityEncoder[F[_]]: EntityEncoder[F, CsCardsRequest] =
    jsonEncoderOf

}
