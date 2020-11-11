package dispatch

import java.nio.file.Path

trait ParamSugar extends RequestSugar {
  def <<(body: String) = {
    subject.setBody(body)
  }

  def <<<(file: Path) = {
    subject.setBody(file)
  }

  def <<?(params: Iterable[(String, String)]) = {
    subject.addQueryParameters(params)
  }

  def <:<(headers: Iterable[(String, String)]) = {
    subject.addHeaders(headers.toSeq)
  }
}
