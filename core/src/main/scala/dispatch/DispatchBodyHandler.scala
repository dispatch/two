package dispatch

import java.net.http.HttpResponse.BodyHandler
import java.net.http.HttpResponse.BodyHandlers

trait DispatchBodyHandler[T] {
  def toBodyHandler(): BodyHandler[T]
}

object ToStrBody extends DispatchBodyHandler[String] {
  def toBodyHandler(): BodyHandler[String] = BodyHandlers.ofString()
}
