package wojmark.cardscores

import org.http4s._
import cats.effect.Async
import com.comcast.ip4s._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
//Renaming imports to avoid namespace collisions/confusion
import org.http4s.server.middleware.{Logger => ServerLogger}
import org.http4s.client.middleware.{Logger => ClientLogger}

import wojmark.cardscores.service.CardsService
import wojmark.cardscores.routes.CardscoresRoutes

object CardscoresServer {

  def run[F[_]: Async]: F[Nothing] = {

    for {

      // Instantiating Ember client
      client <- EmberClientBuilder.default[F].build

      // Logging middleware to enable logging client responses & requests
      clientWithLogger = ClientLogger(logHeaders = true, logBody = true)(
        client
      )

      creditCardsAlgebra = CardsService.implementService[F](
        clientWithLogger,
        // uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards",
        Uri
          .fromString(
            sys.env.getOrElse(
              "CSCARDS_ENDPOINT",
              "https://app.clearscore.com/api/global/backend-tech-test/v1/cards"
            )
          )
          .getOrElse(
            uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards"
          ),
        Uri
          .fromString(
            sys.env.getOrElse(
              "SCOREDCARDS_ENDPOINT",
              "https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
            )
          )
          .getOrElse(
            uri"https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
          )
        // (Uri.fromString(sys.env.get("CSCARDS_ENDPOINT").get).getOrElse(uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards")),
        // uri"https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"
      )

      // uri"https://app.clearscore.com/api/global/backend-tech-test/v1/cards",
      // uri"https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards"

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      httpApp = (
        CardscoresRoutes.creditCardRoutes[F](creditCardsAlgebra)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = ServerLogger.httpApp(true, true)(httpApp)

      _ <-
        EmberServerBuilder
          .default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(
            Port.fromString(sys.env.getOrElse("HTTP_PORT", "8080")).get
          )
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
