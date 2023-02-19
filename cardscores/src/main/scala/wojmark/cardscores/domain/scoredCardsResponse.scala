package wojmark.cardscores.domain

object scoredCardsResonse {

  case class ScoredCardsResponse(
      card: String,
      apr: Double,
      approvalRating: Double
  )
}
