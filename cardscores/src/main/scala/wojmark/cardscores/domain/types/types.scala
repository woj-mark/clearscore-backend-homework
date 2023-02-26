package wojmark.cardscores.types



package object types{

import eu.timepit.refined.numeric._
import eu.timepit.refined.boolean._

    type ZeroToSevenHundred = Not[Less[0]] And Not[Greater[700]]
}
