package dispatch

import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpRequest.BodyPublishers
import java.io.InputStream
import java.nio.file.Path

sealed trait DispatchBodyContent {
  def toBodyPublisher: BodyPublisher
}

object DispatchBodyContent {
  object None extends DispatchBodyContent {
    def toBodyPublisher = BodyPublishers.noBody()
  }

  case class OfByteArray(byteArr: Array[Byte]) extends DispatchBodyContent {
    def toBodyPublisher = BodyPublishers.ofByteArray(byteArr)
  }

  case class OfString(str: String) extends DispatchBodyContent {
    def toBodyPublisher = BodyPublishers.ofString(str)
  }

  case class OfInputStream(is: InputStream) extends DispatchBodyContent {
    def toBodyPublisher = BodyPublishers.ofInputStream(() => is)
  }

  case class OfFile(path: Path) extends DispatchBodyContent {
    def toBodyPublisher = BodyPublishers.ofFile(path)
  }
}
