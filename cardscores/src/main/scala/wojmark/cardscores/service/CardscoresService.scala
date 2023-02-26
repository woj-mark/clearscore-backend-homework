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

//Defines a higher-level interface fot the CardsService implementation
trait CardsService[F[_]] {
  def getScoreCards(cardScoreRequest: CardsRequest): F[List[CardsResponse]]
}

//Implementation of the CardsService module
//CardsService handles the business logic of the /creditCards microservice
//It fetches the responses from the partner microservices wrapped around an effect and applies the data transformation 
// on the obtained data to be returned as a response exposed in the HTTP Routes in a POST method
object CardsService {

  //Enables creating CardsService by implicitly resolving an instance of CardsService[F]
  //For example, if if there is an implicit CardsService[IO] in scope, an instance of CardsService[IO] 
  //can be created
  def apply[F[_]](implicit cc: CardsService[F]): CardsService[F] = cc


  //A helper method to create instance of CardsResponse from CSCardsResponse
  def csCardsToCreditCard(csCardsResponse: CsCardsResponse): CardsResponse = {
    CardsResponse(
      "CSCards",
      csCardsResponse.cardName,
      csCardsResponse.apr,
      CardScore.fromCSCards(csCardsResponse.apr, csCardsResponse.eligibility)
    )
  }

  //A helper method to create instance of CardsResponse from ScoredCardsResponse
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

  // Helper method to sort credit cards for the /creditCards API response
  def sortCreditCards(
      creditCards: List[CardsResponse]
  ): List[CardsResponse] = {

    //Descending order, from highest to lowest
    val reverseOrder = Ordering[Double].reverse

    creditCards.sortBy(card => card.cardScore)(reverseOrder)
  }

  def implementService[F[_]: Concurrent](
      client: Client[F],
      csCardsEndpoint: Uri,
      scoredCardsEndpoint: Uri
  ): CardsService[F] = {
    new CardsService[F] {

      val dsl: Http4sClientDsl[F] = new Http4sClientDsl[F] {}
      import dsl._

      def getScoreCards(cardReq: CardsRequest): F[List[CardsResponse]] = {

        // Composing  requests to the partner microservices
        val csCardsRequest: CsCardsRequest =
          CsCardsRequest(cardReq.name, cardReq.creditScore)

        val scoredCardsRequest: ScoredCardsRequest =
          ScoredCardsRequest(cardReq.name, cardReq.creditScore, cardReq.salary)

        
        for {

          //Gets an F-wrapped list of CsCardsResponses from CsCards microservice
          csCards <- client.expect[List[CsCardsResponse]](
            POST(
              csCardsRequest,
              csCardsEndpoint,
              headers =
                Headers(`User-Agent`(Config.getProductId("USER_AGENT_ID")))
            )
          )

          //Gets an F-wrapped list of CsCardsResponses from ScoredCards microservice
          scoredCards <- client.expect[List[ScoredCardsResponse]](
            POST(
              scoredCardsRequest,
              scoredCardsEndpoint,
              headers =
                Headers(`User-Agent`(Config.getProductId("USER_AGENT_ID")))
            )
          )

          //Converting the instances of the microservice responses to CreditCardResponse instance
          csCreditCards = csCards.map(csCards => csCardsToCreditCard(csCards))
          scoredCardsCreditCards = scoredCards.map(scCards =>
            scoredCardsToCreditCard(scCards)
          )

          //Combines the F-wrapped lists of the CreditCardResponse and sort them accordingly
          creditCardsCombinedSorted = sortCreditCards(
            csCreditCards ++ scoredCardsCreditCards
          )

        } yield creditCardsCombinedSorted

      }
    }
  }
}
