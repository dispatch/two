package dispatch

import java.net.http.HttpRequest
import java.net.http.HttpRequest.Builder
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import scala.collection.Map

case class DispatchRequestBuilder(
  method: String = "GET",
  methodExplicitlySet: Boolean = false,
  headers: Map[String, String] = Map.empty,
  queryParams: Map[String, String] = Map.empty,
  url: String = "",
  bodyContent: DispatchBodyContent = DispatchBodyContent.None,
  extraBuilderFuncs: HttpRequest.Builder => HttpRequest.Builder = identity
) {
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

    if(! headers.contains("Content-Type")) {
      // Default content type header
      builder.setHeader("Content-Type", "text/plain; charset=utf8")
    }

    builder = extraBuilderFuncs(builder)

    builder.build()
  }
}