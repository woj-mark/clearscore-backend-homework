package wojmark.cardscores

//import wojmark.cardscores.domain.cardScore.CardScore

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
//   test(
//     "Test conversion of Scored Cards response to Credit Scores Response in accordance to spec"
//   ) {
//   {
//     "card": "ScoredCard Builder",
//     "apr": 19.4,
//     "approvalRating": 0.8`
//   }

// {
//     "provider": "ScoredCards"
//     "name": "ScoredCard Builder",
//     "apr": 19.4,
//     "cardScore": 0.212
//   },
//}

//   test(
//     "Test conversion of CSCards response to Credit Scores Response in accordance to spec"
//   ) {
// //       {
//     "cardName": "SuperSaver Card",
//     "apr": 21.4,
//     "eligibility": 6.3
//   },
//   {
//     "cardName": "SuperSpender Card",
//     "apr": 19.2,
//     "eligibility": 5.0
//   }

//to

//  {
//     "provider": "CSCards",
//     "name": "SuperSaver Card",
//     "apr": 21.4,
//     "cardScore": 0.137
//   },
//   {
//     "provider": "CSCards",
//     "name": "SuperSpender Card",
//     "apr": 19.2,
//     "cardScore": 0.135

//   }

//   }
// }
