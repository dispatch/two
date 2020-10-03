package dispatch;

import java.util.Base64

trait AuthSugar extends RequestSugar {
  val base64Encoder = Base64.getEncoder()

  /**
   * Set the realm header on the request.
   */
  def setRealm(realm: String): Req = {
    subject.removeHeaders("Realm").addHeader("Realm", realm)
  }

  /**
   * Set the authorization header on the request.
   */
  def setAuthorization(authType: String, authContent: String): Req = {
    subject
      .removeHeaders("Authorization")
      .addHeader("Authorization", "%s %s".format(authType, authContent))
  }

  /**
   * Use Basic authentication
   */
  def asBasic(user: String, password: String): Req = {
    val authContent = "%s:%s".format(user, password).getBytes("UTF-8")
    val encodedAuthContent = base64Encoder.encodeToString(authContent)
    setAuthorization("Basic", encodedAuthContent)
  }

  /**
   * Use Bearer token authentication.
   */
  def asBearer(token: String): Req = {
    setAuthorization("Bearer", token)
  }
}
