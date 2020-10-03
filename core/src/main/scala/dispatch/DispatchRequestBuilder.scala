package dispatch

import java.net.http.HttpRequest
import java.net.http.HttpRequest.Builder
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import scala.collection.Map
import scala.collection.Seq

case class DispatchRequestBuilder(
  method: String = "GET",
  methodExplicitlySet: Boolean = false,
  headers: Seq[(String, String)] = Seq.empty,
  queryParams: Map[String, String] = Map.empty,
  url: String = "",
  bodyContent: DispatchBodyContent = DispatchBodyContent.None,
  extraBuilderFuncs: HttpRequest.Builder => HttpRequest.Builder = identity
) {
  private val CONTENT_TYPE_HEADER_NAME = "Content-Type"
  private val DEFAULT_CONTENT_TYPE = "text/plain; charset=utf8"
  private val USER_AGENT_HEADER_NAME = "User-Agent"
  private val DEFAULT_USER_AGENT_PREFIX = "Dispatch/"

  def build: HttpRequest = {
    val fullQueryParams = queryParams.map({
      case (key, value) if ! value.nonEmpty =>
        key
      case (key, value) =>
        val encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8)
        s"$key=$encodedValue"
    }).iterator.mkString("&")

    val finalUrl = if (queryParams.nonEmpty) {
      url + "?" + fullQueryParams
    } else {
      url
    }

    var builder = HttpRequest.newBuilder()
      .method(method, bodyContent.toBodyPublisher)
      .uri(new URI(finalUrl))

    headers.foreach {
      case (name, value) =>
        builder = builder.setHeader(name, value)
    }

    if(! headers.contains(CONTENT_TYPE_HEADER_NAME)) {
      // Default content type header
      builder.setHeader(CONTENT_TYPE_HEADER_NAME, DEFAULT_CONTENT_TYPE)
    }

    if(! headers.contains(USER_AGENT_HEADER_NAME)) {
      // Default user agent header
      builder.setHeader(USER_AGENT_HEADER_NAME, "%s%s".format(DEFAULT_USER_AGENT_PREFIX, BuildInfo.version))
    }

    builder = extraBuilderFuncs(builder)

    builder.build()
  }
}
