package dispatch

trait HeaderSugar extends RequestSugar {
  /**
   * Append a collecton of headers to the headers already
   * on the request.
   */
  def <:< (hs: Iterable[(String,String)]) = {
    hs.foldLeft(subject) {
      case (s, (key, value)) =>
        s.setHeader(key, value)
    }
  }

  /**
   * Append a collecton of headers to the headers already
   * on the request.
   */
  def appendHeaders (hs: Iterable[(String, String)]) =
    <:<(hs)
}
