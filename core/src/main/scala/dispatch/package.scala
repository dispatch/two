package dispatch

trait HostReqCreator {
  def scheme: String

  /**
   * Set the URL to target a specific hostname.
   */
  def apply(host: String) = {
    Req().setUrl("%s://%s/".format(scheme, host))
  }

  /**
   * Set the url to target a specific hostname and port.
   */
  def apply(host: String, port: Int) = {
    Req().setUrl("%s://%s:%d/".format(scheme, host, port))
  }
}

trait PlaintextHostReqCreator extends HostReqCreator {
  val scheme = "http"
}

object :/ extends PlaintextHostReqCreator
object host extends PlaintextHostReqCreator

trait SecureHostReqCreator extends HostReqCreator {
  val scheme = "https"
}

object hostSecure extends SecureHostReqCreator

object url extends (String => Req) {
  /**
   * Set the hostname to target a specific, complete URL.
   */
  def apply(url: String): Req = {
    Req().setUrl(url)
  }
}
