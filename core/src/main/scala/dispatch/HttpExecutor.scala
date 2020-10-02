package dispatch

import java.io.InputStream
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

trait HttpExecutor {
  self =>
  def client: HttpClient

  def apply(req: Req)(implicit executor: ExecutionContext): Future[HttpResponse[InputStream]] = {
     apply(req, BodyHandlers.ofInputStream())
  }

  def apply[T](req: Req, handler: HttpResponse.BodyHandler[T])(implicit executor: ExecutionContext): Future[HttpResponse[T]] = {
    apply(req.toRequest, handler)
  }

  def apply[T](pair: (HttpRequest, HttpResponse.BodyHandler[T]))
              (implicit executor: ExecutionContext): Future[T] = {
    apply(pair._1, pair._2)
  }

  def apply[T](request: HttpRequest, handler: HttpResponse.BodyHandler[T])(implicit executor: ExecutionContext): Future[HttpResponse[T]] = {
    val fut = client.sendAsync(request, handler)
    val promise = scala.concurrent.Promise[HttpResponse[T]]()

    fut.handleAsync( (result, ex) => {
      if (ex != null) {
        promise.failure(ex)
      } else {
        promise.success(result)
      }
    })

    promise.future
  }
}
