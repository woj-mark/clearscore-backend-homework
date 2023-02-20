package wojmark.cardscores.domain

object csCardsResponse {

  case class CsCardsResponse(
      cardName: String,
      apr: Double,
      eligibility: Double
  )

}
