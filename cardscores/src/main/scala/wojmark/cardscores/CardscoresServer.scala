package wojmark.cardscores

import cats.effect.Async
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

      // Logging middleware to enable logging client's requests & responses
      clientWithLogger = ClientLogger(logHeaders = true, logBody = true)(
        client
      )

      creditCardsAlgebra = CardsService.implementService[F](
        clientWithLogger,
        Config.getUriByServiceName("CSCARDS_ENDPOINT"),
        Config.getUriByServiceName("SCOREDCARDS_ENDPOINT")
      )

            httpApp = (
        CardscoresRoutes.creditCardRoutes[F](creditCardsAlgebra)
      ).orNotFound

      // A logger for the Server side
      finalHttpApp = ServerLogger.httpApp(true, true)(httpApp)

      _ <-
        EmberServerBuilder
          .default[F]
          .withHost(Config.getHost("HOST_ADDRESS"))
          .withPort(
            Config.getPort("HTTP_PORT")
          )
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
