package dispatch;

trait UrlSugar extends RequestSugar {
  /**
   * Retrieve the fully materialized URL.
   */
  def url: String = {
    subject.requestBuilder.url
  }

  /**
   * Append a segment to the URL.
   */
  def / (segment: String): Req = {
    if (segment.nonEmpty) {
      subject.setUrl(url + "/" + segment)
    } else {
      subject
    }
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
