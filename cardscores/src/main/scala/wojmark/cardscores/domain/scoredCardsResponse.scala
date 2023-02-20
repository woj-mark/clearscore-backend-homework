package wojmark.cardscores.domain

object scoredCardsResponse {

  case class ScoredCardsResponse(
      card: String,
      apr: Double,
      approvalRating: Double
  )
}
