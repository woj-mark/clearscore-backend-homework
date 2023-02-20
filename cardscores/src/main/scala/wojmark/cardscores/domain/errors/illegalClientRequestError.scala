package wojmark.cardscores.domain.errors

object illegalClientRequestError {
  case class IllegalClientRequestError(message: String)
}
