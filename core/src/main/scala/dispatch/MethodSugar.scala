package dispatch

trait MethodSugar extends RequestSugar {
  def HEAD    = subject.setMethod("HEAD")
  def GET     = subject.setMethod("GET")
  def POST    = subject.setMethod("POST")
  def PUT     = subject.setMethod("PUT")
  def DELETE  = subject.setMethod("DELETE")
  def PATCH   = subject.setMethod("PATCH")
  def TRACE   = subject.setMethod("TRACE")
  def OPTIONS = subject.setMethod("OPTIONS")
}
