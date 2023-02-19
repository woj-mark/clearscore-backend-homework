package wojmark.cardscores

class BusinessLogicTests extends munit.FunSuite {

  test(
    "Test calculation of the sorting score for Score Cards"
  ) {
    // Edge cases, eligibility zero, max, always have to be 3 decimals
  }

  test(
    "Test calculation of the sorting score for CSCards"
  ) {
    // Edge cases, eligibility zero, max, always have to be 3 decimals
  }

  test(
    "Test conversion of Scored Cards response to Credit Scores Response in accordance to spec"
  ) {
    //   {
//     "card": "ScoredCard Builder",
//     "apr": 19.4,
//     "approvalRating": 0.8
//   }

// {
//     "provider": "ScoredCards"
//     "name": "ScoredCard Builder",
//     "apr": 19.4,
//     "cardScore": 0.212
//   },
  }

  test(
    "Test conversion of CSCards response to Credit Scores Response in accordance to spec"
  ) {
//       {
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

  }
}
