package wojmark.cardscores.domain

import io.circe.generic.auto._
import cats.effect.Concurrent

import org.http4s._
import org.http4s.circe._

object scoredCardsResponse {

/**
 * Data model for CardSearchResponse from Swagger definition of the ScoredCards microservice.
 */

  case class ScoredCardsResponse(
      card: String,
      apr: Double,
      approvalRating: Double
  )

  //'jsonDecoderOf' constructs an EntityDecoder that can at can decode a JSON-encoded message body
  // to a case class instance of the ScoredCardsResponse
  // The Entity Decoder helps with the streaming nature of the way how http4s
  // handles requests (Request[F]) and responses (Responses[F]) in a streaming fashion.

  implicit def scoredCardsResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, ScoredCardsResponse] =
    jsonOf

  implicit def scoredCardsListResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, List[ScoredCardsResponse]] =
    jsonOf

}
