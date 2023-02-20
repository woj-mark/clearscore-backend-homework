package wojmark.cardscores.service

// import org.http4s.client._
// import cats.effect.Concurrent
// import org.http4s.client.dsl.Http4sClientDsl

import wojmark.cardscores.domain.cardsRequest.CardsRequest
import wojmark.cardscores.domain.cardsResponse.CardsResponse
import wojmark.cardscores.domain.csCardsResponse.CsCardsResponse
import wojmark.cardscores.domain.scoredCardsResponse.ScoredCardsResponse

import wojmark.cardscores.CardScore

trait CardsService[F[_]] {
  def getScoreCards(cardScoreRequest: CardsRequest): F[List[CardsResponse]]
}

object CardsService {
  def apply[F[_]](implicit cc: CardsService[F]): CardsService[F] = cc

  def csCardsToCreditCard(csCardsResponse: CsCardsResponse): CardsResponse = {
    CardsResponse(
      "CSCards",
      csCardsResponse.cardName,
      csCardsResponse.apr,
      CardScore.fromCSCards(csCardsResponse.apr, csCardsResponse.eligibility)
    )
  }

  def scoredCardsToCreditCard(scoredCardsResponse: ScoredCardsResponse) = {
    CardsResponse(
      "ScoredCards",
      scoredCardsResponse.card,
      scoredCardsResponse.apr,
      CardScore.fromScoredCards(
        scoredCardsResponse.apr,
        scoredCardsResponse.approvalRating
      )
    )
  }

//Function implement service- this will contain  a function which will handle the whole API request logic
//It will receive the request to /creditcards API and parse them to the client

//   def implementService[F[_]: Concurrent](
//       client: Client[F],
//       csCardsEndpoint: String,
//       scoredCardsEndpoint: String
//   ): CardsService[F] = {
//     new CardsService[F] {

//       val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F] {}
//       import dsl._
//     }

  // }

}
