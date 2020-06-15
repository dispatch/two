package dispatch

object Defaults {
  implicit def executor = scala.concurrent.ExecutionContext.Implicits.global
}
