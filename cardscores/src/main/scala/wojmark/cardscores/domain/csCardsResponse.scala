package wojmark.cardscores.domain

import io.circe.generic.auto._
import cats.effect.Concurrent

import org.http4s._
import org.http4s.circe._

object csCardsResponse {

/**
 * Data model for CardSearchResponse from Swagger definition of the CSCards microservice.
 */

  case class CsCardsResponse(
      cardName: String,
      apr: Double,
      eligibility: Double
  )


  
  //'jsonDecoderOf' constructs an EntityDecoder that can at can decode a JSON-encoded message body
  // to a case class instance of the CsCardsResponse
  // The Entity Decoder helps with the streaming nature of the way how http4s
  // handles requests (Request[F]) and responses (Responses[F]) in a streaming fashion.

   // Type class constraint [F[_]: Concurrent] declares a constraint on a higher-kinded type F that requires an implicit
  // instance of cats-effect 'Concurrent' that  provides abstractions for effectful programming in Scala 
  // adopted in this project. This constraint ensures that the appropriate instances of the Concurrent 
  // type class are available in the implicit scope.
  implicit def csCardsResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, CsCardsResponse] =
    jsonOf

  implicit def csCardsListResponseEntityDecoder[F[_]: Concurrent]
      : EntityDecoder[F, List[CsCardsResponse]] =
    jsonOf

}
