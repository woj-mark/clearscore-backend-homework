package wojmark.cardscores.domain

object csCardsRequest {

  case class CsCardsRequest(
      name: String,
      creditScore: Int
  )
}
