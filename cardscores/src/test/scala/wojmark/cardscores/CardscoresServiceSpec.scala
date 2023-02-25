package wojmark.cardscores
import wojmark.cardscores.domain.cardsResponse.CardsResponse
import wojmark.cardscores.domain.csCardsResponse.CsCardsResponse
import wojmark.cardscores.domain.scoredCardsResponse.ScoredCardsResponse

import wojmark.cardscores.service.CardsService

class CardscoresServiceSpec extends munit.FunSuite {

  test(
    "Test if sortCreditCards returns sorted cards in reverse order of cardScore"
  ) {
    val unsortedCards: List[CardsResponse] = List(
      CardsResponse("CSCards", "SuperSaver Card", 21.4, 0.137),
      CardsResponse("ScoredCards", "ScoredCard Builder", 19.4, 0.212),
      CardsResponse("CSCards", "SuperSpender Card", 19.2, 0.135)
    )

    val sortedCards: List[CardsResponse] = List(
      CardsResponse("ScoredCards", "ScoredCard Builder", 19.4, 0.212),
      CardsResponse("CSCards", "SuperSaver Card", 21.4, 0.137),
      CardsResponse("CSCards", "SuperSpender Card", 19.2, 0.135)
    )
    assert(
      sortedCards == CardsService.sortCreditCards(unsortedCards),
      "Sorting of the Card Responses is incorrect"
    )
  }

  test(
    "Test if csCardsToCreditCard correctly converts a csCardResponse to a CreditCardResponse"
  ) {
    val csCardResponse1 =
      CsCardsResponse("SuperSaver Card", 21.4, 6.3)

    val csCardResponse2 =
      CsCardsResponse("SuperSpender Card", 19.2, 5.0)

    val creditCardResponse1 =
      CardsResponse("CSCards", "SuperSaver Card", 21.4, 0.137)

    val creditCardResponse2 =
      CardsResponse("CSCards", "SuperSpender Card", 19.2, 0.135)

    assert(
      creditCardResponse1 == CardsService.csCardsToCreditCard(csCardResponse1),
      "Converting to CreditCards from CsCards is incorrect"
    )

    assert(
      creditCardResponse2 == CardsService.csCardsToCreditCard(csCardResponse2),
      "Converting to CreditCards from CsCards is incorrect"
    )
  }

  test(
    "Test if scoredCardsToCreditCard correctly converts a ScoresCardResponse to a CreditCardResponse"
  ) {
    val scoredCardResponse =
      ScoredCardsResponse("ScoredCard Builder", 19.4, 0.8)

    val creditCardResponseSC =
      CardsResponse("ScoredCards", "ScoredCard Builder", 19.4, 0.212)

    assert(
      creditCardResponseSC == CardsService.scoredCardsToCreditCard(
        scoredCardResponse
      ),
      "Converting to CreditCards from ScoredCards is incorrect"
    )
  }
}
