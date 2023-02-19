package wojmark.cardscores.domain

object scoredCardsRequest {

  case class ScoredCardsRequest(
      name: String,
      score: Double,
      salary: Int
  )
}
