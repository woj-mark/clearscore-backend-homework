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

/**
 * Definition of the HTTP routes for handling the POST requests to /creditCards API endpoint 
 */
object CardscoresRoutes {

    def creditCardRoutes[F[_]: Concurrent](
      cardsService: CardsService[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case request @ POST -> Root / "creditCards" =>

      //Attempt enables error-handling on the decoded CardsRequest (or rather the effect of it)
      request.decodeJson[CardsRequest].attempt.flatMap {

        //If decoding successful, return a response with 200 OK Status Code
        // containing the sorted cards [CardsResponse]
        case Right(correctRequest) =>
          for {
            sortedCreditCards <- cardsService.getScoreCards(correctRequest)
            response <- Ok(sortedCreditCards)
          } yield response

        //If error, return a 400 Status Bad Request
        case Left(incorrectRequest) =>
          incorrectRequest.getCause match {
            case failure: DecodeFailure => BadRequest(failure.message)
            //The failure would occur because of failed decoding caused by
            //failed compile-time validation of user inputs. In such case,
            // Swagger definition of /creditCards requireds the following message within 400 Bad Request Response:
            case _                      => BadRequest("The request contained invalid parameters")
          }

      }
    }
  }
}

