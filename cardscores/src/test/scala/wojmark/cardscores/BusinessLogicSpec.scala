package wojmark.cardscores


class BusinessLogicTests extends munit.FunSuite {

  test(
    "Test calculation of the card score for CSCards (SuperSaver) "
  ) {
    val csCardScoreSuperSaver = CardScore.fromCSCards(21.4, 6.3)

    assert(
      csCardScoreSuperSaver == 0.137,
      "CSCards (SuperSaver) score is incorrect"
    )
  }

  test(
    "Test calculation of the card score for CSCards (Super Spender)"
  ) {
    val csCardScoreSuperSpender = CardScore.fromCSCards(19.2, 5.0)
    assert(
      csCardScoreSuperSpender == 0.135,
      "CSCards (SuperSpender) score is incorrect"
    )

  }

  test(
    "Test calculation of the card score for ScoredCards (ScoreCard Builder)"
  ) {
    val scoredCardsScoreCardBuilder = CardScore.fromScoredCards(19.4, 0.8)
    assert(
      scoredCardsScoreCardBuilder == 0.212,
      "CSCards (SuperSpender) score is incorrect"
    )
  }
}
