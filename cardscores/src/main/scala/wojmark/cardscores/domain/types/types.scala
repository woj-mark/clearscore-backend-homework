package wojmark.cardscores.types



package object types{

import eu.timepit.refined.numeric._
import eu.timepit.refined.boolean._


//A numeric type alias which enables constraining the cardScores within a range between 0 and 700 
    type ZeroToSevenHundred = Not[Less[0]] And Not[Greater[700]]
}
