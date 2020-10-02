package dispatch;

trait UrlSugar extends RequestSugar {
  /**
   * Retrieve the fully materialized URL.
   */
  def url: String = {
    subject.toRequest.uri().toASCIIString()
  }

  /**
   * Append a segment to the URL.
   */
  def / (segment: String): Req = {
    subject.setUrl(url + "/" + segment)
  }

  /**
   * Append a segment to the URL.
   */
  def appendSegment(segment: String): Req = {
    /(segment)
  }

  /**
   * Append a segment that may or may not occur to the URL.
   */
  def /? (segmentOpt: Option[String]): Req = {
    segmentOpt.map(this / _).getOrElse(subject)
  }

  /**
   * Append a segment that may or may not occur to the URL.
   */
  def appendOptionalSegment(segmentOpt: Option[String]): Req =
    /?(segmentOpt)
}
