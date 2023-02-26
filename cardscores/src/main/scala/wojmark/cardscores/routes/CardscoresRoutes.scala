package wojmark.cardscores.routes

import cats.effect.Concurrent
import cats.implicits._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.{DecodeFailure, HttpRoutes}
import wojmark.cardscores.service.CardsService
import wojmark.cardscores.domain.cardsRequest.CardsRequest

object CardscoresRoutes {

    def creditCardRoutes[F[_]: Concurrent](
      cardsService: CardsService[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case request @ POST -> Root / "creditCards" =>

      request.decodeJson[CardsRequest].attempt.flatMap {
        case Right(correctRequest) =>
          for {
            sortedCreditCards <- cardsService.getScoreCards(correctRequest)
            response <- Ok(sortedCreditCards)
          } yield response

        case Left(incorrectRequest) =>
          incorrectRequest.getCause match {
            case failure: DecodeFailure => BadRequest(failure.message)
            case _                      => BadRequest(incorrectRequest.toString)
          }

      }
    }
  }
}

