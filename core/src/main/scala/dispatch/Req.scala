package dispatch

import java.nio.file.Path
import java.io.InputStream
import java.nio.charset.Charset
import java.net.http.HttpRequest
import java.net.URI

/**
  * This wrapper provides referential transparency for the
  * underlying RequestBuilder.
  */
case class Req(
  requestBuilder: DispatchRequestBuilder = DispatchRequestBuilder()
) extends AuthSugar with UrlSugar with MethodSugar with ParamSugar {
  def subject = this

  /**
   * Append a transform onto the underlying Java request builder
   */
  def underlying(next: HttpRequest.Builder => HttpRequest.Builder) = {
    Req(requestBuilder.copy(
      extraBuilderFuncs = requestBuilder.extraBuilderFuncs.andThen(next)
    ))
  }

  /**
   * Convert this to a concrete request.
   */
  def toRequest = {
    requestBuilder.build
  }

  /**
   * Set the HTTP method
   */
  def setMethod(method: String) = {
    Req(requestBuilder.copy(
      method = method,
      methodExplicitlySet = true
    ))
  }

  /**
   * Set method unless method has been explicitly set using [[setMethod]].
   */
  def implyMethod(method: String) = {
    if (! requestBuilder.methodExplicitlySet) {
      Req(requestBuilder.copy(
        method = method
      ))
    } else {
      subject
    }
  }

  /**
   * Set the url of the request
   */
  def setUrl(url: String) = {
    Req(requestBuilder.copy(
      url = url
    ))
  }

  /**
   * Add a header
   */
  def addHeader(name: String, value: String) = {
    Req(requestBuilder.copy(
      headers = requestBuilder.headers.concat(Map(name -> value))
    ))
  }

  /**
   * Add multiple headers
   */
  def addHeaders(headers: Seq[(String, String)]) = {
    Req(requestBuilder.copy(
      headers = requestBuilder.headers.concat(headers)
    ))
  }

  /**
   * Remove all headers with a specific name
   */
  def removeHeaders(name: String) = {
    Req(requestBuilder.copy(
      headers = requestBuilder.headers.filterNot(_._1.equals(name))
    ))
  }

  /**
   * Set the request body from a byte array.
   */
  def setBody(data: Array[Byte]) = {
    Req(requestBuilder.copy(
      bodyContent = DispatchBodyContent.OfByteArray(data),
      headers = requestBuilder.headers :+ ("Content-Type" -> "text/plain; charset=UTF-8")
    )).implyMethod("POST")
  }

  /**
   * Set the request body using a InputStream.
   */
  def setBody(inputStream: InputStream) = {
    Req(requestBuilder.copy(
      bodyContent = DispatchBodyContent.OfInputStream(inputStream)
    )).implyMethod("POST")
  }

  /**
   * Set the request body using a string.
   */
  def setBody(data: String) = {
    Req(requestBuilder.copy(
      bodyContent = DispatchBodyContent.OfString(data)
    )).implyMethod("POST")
  }

  /**
   * Set the request body to the contents of a File.
   */
  def setBody(file: Path) = {
    Req(requestBuilder.copy(
      bodyContent = DispatchBodyContent.OfFile(file)
    )).implyMethod("PUT")
  }

  /**
   * Add a new query parameter to the request.
   */
  def addQueryParameter(name: String, value: String) = {
    Req(requestBuilder.copy(
      queryParams = requestBuilder.queryParams.concat(Map(name -> value))
    ))
  }

  /**
   * Add a new query parameter to the request without a value. This is useful
   * for some APIs that require adding just the key to the query parameters
   */
  def addQueryParameter(name: String) = {
    Req(requestBuilder.copy(
      queryParams = requestBuilder.queryParams.concat(Map(name -> ""))
    ))
  }

  def addQueryParameters(params: Iterable[(String, String)]) = {
    Req(requestBuilder.copy(
      queryParams = requestBuilder.queryParams.concat(params)
    ))
  }

  /**
   * Set query parameters, overwriting any pre-existing query parameters.
   */
  def setQueryParameters(params: Iterable[(String, String)]) = {
    Req(requestBuilder.copy(
      queryParams = params
    ))
  }

}

object Req {

}
