package wojmark.cardscores.domain

import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import eu.timepit.refined.collection.NonEmpty

import eu.timepit.refined.api.Refined
import io.circe.refined._ 
//import eu.timepit.refined.types.string.NonEmptyString



object csCardsRequest {

  case class CsCardsRequest(
      name: String Refined NonEmpty,
      creditScore: Int
  )

  implicit
  def csCardsRequestEntityEncoder[F[_]]: EntityEncoder[F, CsCardsRequest] =
    jsonEncoderOf

}
