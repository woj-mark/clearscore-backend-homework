package wojmark.cardscores

//import org.http4s._
import cats.effect.Async
//import com.comcast.ip4s._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
// import io.circe.refined._
// import io.circe.literal._

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
          .withHost(Config.getHost("HOST_ADDRESS"))
          .withPort(
            Config.getPort("HTTP_PORT")
          )
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
