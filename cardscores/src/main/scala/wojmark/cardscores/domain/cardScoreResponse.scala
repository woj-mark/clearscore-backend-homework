package wojmark.cardscores.domain

object cardScoreResponse {
  // As defined in the Swagger API documentation
//Currently using Scala basic types for name, credit score and salary
//TO DO: use Refined to contrain the types for the parameters

  case class CreditCardResponse(
      provider: String,
      name: String,
      apr: Double,
      cardScore: Double
  )
}
