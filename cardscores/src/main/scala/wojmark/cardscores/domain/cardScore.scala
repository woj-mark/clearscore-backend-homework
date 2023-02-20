package wojmark.cardscores

object CardScore {

  def fromCSCards(apr: Double, eligibility: Double): Double = {
    10 * eligibility * (scala.math.pow((1 / apr), 2))
  }

  def fromScoredCards(apr: Double, approvalRating: Double) = {
    100 * approvalRating * (scala.math.pow((1 / apr), 2))
  }

}
