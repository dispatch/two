package dispatch

import java.net.http.HttpClient

/** Http executor with defaults */
case class Http(
  clientBuilder: HttpClient.Builder
) extends HttpExecutor {
  lazy val client = clientBuilder.build

  /**
   * Returns a new instance replacing the underlying `clientBuilder` with a new instance that is
   * configured using the `withBuilder` provided. The underlying client for this instance is closed
   * before the new instance is created in order to avoid resource leaks.
   */
  def closeAndConfigure(withBuilder: HttpClient.Builder => HttpClient.Builder): Http = {
    copy(clientBuilder = withBuilder(HttpClient.newBuilder()))
  }
}

/**
 * Singleton helper for vending Http instances.
 */
object Http {
  val defaultClientBuilder = HttpClient.newBuilder()

  /**
   * The default executor.
   */
  lazy val default: Http = {
    Http(defaultClientBuilder)
  }

  /**
   * Create an Http executor with some custom configuration defined by the `withBuilder` function
   * that will mutate the underlying `Builder` from AHC.
   */
  def withConfiguration(withBuilder: HttpClient.Builder => HttpClient.Builder): Http = {
    Http(withBuilder(HttpClient.newBuilder))
  }
}
