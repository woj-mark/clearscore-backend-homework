package wojmark.cardscores

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = CardscoresServer.run[IO]
}
