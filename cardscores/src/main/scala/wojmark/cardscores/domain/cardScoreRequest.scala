package wojmark.cardscores.domain

//As defined in the Swagger API documentation
//Currently using Scala basic types for name, credit score and salary
//TO DO: use Refined to contrain the types for the parameters

object cardScoreRequest {

  case class CreditCardRequest(
      name: String,
      creditScore: Int,
      salary: Int
  )
}
