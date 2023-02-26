package wojmark.cardscores.service

import org.http4s.client._
import cats.effect.Concurrent
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.io.POST
import org.http4s.Uri
import cats.implicits._
import org.http4s.headers._
import wojmark.cardscores.Config

import wojmark.cardscores.domain.cardsRequest.CardsRequest
import wojmark.cardscores.domain.cardsResponse.CardsResponse
import wojmark.cardscores.domain.csCardsRequest.CsCardsRequest
import wojmark.cardscores.domain.csCardsResponse.CsCardsResponse
import wojmark.cardscores.domain.scoredCardsRequest.ScoredCardsRequest
import wojmark.cardscores.domain.scoredCardsResponse.ScoredCardsResponse

import wojmark.cardscores.CardScore
import org.http4s.Headers

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

  // Helper function to sort credit cards for the /creditCards API response
  def sortCreditCards(
      creditCards: List[CardsResponse]
  ): List[CardsResponse] = {

    val reverseOrder = Ordering[Double].reverse
    creditCards.sortBy(card => card.cardScore)(reverseOrder)
  }

//Function implement service- this will contain  a function which will handle the whole API request logic
//It will receive the request to /creditcards API and parse them to the client

  def implementService[F[_]: Concurrent](
      client: Client[F],
      csCardsEndpoint: Uri,
      scoredCardsEndpoint: Uri
  ): CardsService[F] = {
    new CardsService[F] {

      val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F] {}
      import dsl._

      def getScoreCards(cardReq: CardsRequest): F[List[CardsResponse]] = {

        // Composing  request to the partners
        val csCardsRequest: CsCardsRequest =
          CsCardsRequest(cardReq.name, cardReq.creditScore)

        val scoredCardsRequest: ScoredCardsRequest =
          ScoredCardsRequest(cardReq.name, cardReq.creditScore, cardReq.salary)

        for {
          csCards <- client.expect[List[CsCardsResponse]](
            POST(
              csCardsRequest,
              csCardsEndpoint,
              headers =
                Headers(`User-Agent`(Config.getProductId("USER_AGENT_ID")))
            )
          )
          scoredCards <- client.expect[List[ScoredCardsResponse]](
            POST(
              scoredCardsRequest,
              scoredCardsEndpoint,
              headers =
                Headers(`User-Agent`(Config.getProductId("USER_AGENT_ID")))
            )
          )

          csCreditCards = csCards.map(csCards => csCardsToCreditCard(csCards))
          scoredCardsCreditCards = scoredCards.map(scCards =>
            scoredCardsToCreditCard(scCards)
          )

          creditCardsCombinedSorted = sortCreditCards(
            csCreditCards ++ scoredCardsCreditCards
          )

        } yield creditCardsCombinedSorted

      }
    }
  }
}
