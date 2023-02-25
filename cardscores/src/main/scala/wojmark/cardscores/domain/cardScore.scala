package wojmark.cardscores

object CardScore {

  /** ASSUMPTION: I recognised that the returning cardScore are returned with
    * precision to 3 significant figures (digitts) as well as rounded using
    * floor function. For example, equation gives a result of 0.21256244 and the
    * creditCards API shall return 0.212. For this reason, I'm using floor
    * rounding and return 3 significant figures to obtain the creditScore. I've
    * decided to use BigDecimal from scala standard library to perform these
    * operations on floating-point numbers.
    */

  // A function converting the parameters from CSCards API response
  // to a cardScore (Double) for CardsResponse
  def fromCSCards(apr: Double, eligibility: Double): Double = {
    BigDecimal(10 * eligibility * (scala.math.pow((1 / apr), 2)))
      .setScale(3, BigDecimal.RoundingMode.FLOOR)
      .toDouble
  }

  // A function converting the parameters from ScoredCards API response
  // to a cardScore (Double) for CardsResponse
  def fromScoredCards(apr: Double, approvalRating: Double): Double = {
    BigDecimal(100 * approvalRating * (scala.math.pow((1 / apr), 2)))
      .setScale(3, BigDecimal.RoundingMode.FLOOR)
      .toDouble
  }

}
