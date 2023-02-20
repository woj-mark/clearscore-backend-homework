package wojmark.cardscores.domain

object cardsResponse {
  // As defined in the Swagger API documentation
//Currently using Scala basic types for name, credit score and salary
//TO DO: use Refined to contrain the types for the parameters

  case class CardsResponse(
      provider: String,
      name: String,
      apr: Double,
      cardScore: Double
  )
}
