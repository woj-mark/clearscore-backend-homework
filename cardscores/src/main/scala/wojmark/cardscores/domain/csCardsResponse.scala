package wojmark.cardscores.domain

object csCardsResponse {

  object csCardsRresponse {

    case class CsCardsRequest(
        cardName: String,
        apr: Double,
        eligibility: Double
    )
  }
}
