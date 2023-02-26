package wojmark.cardscores.domain


object cardsResponse {
  
/**
 * Data model for CreditCardResponse from Swagger definition of the /creditCards microservice.
*/

  case class CardsResponse(
      provider: String,
      name: String,
      apr: Double,
      cardScore: Double
  )
}
