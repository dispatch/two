import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "2.0.0-SNAPSHOT"
ThisBuild / organization     := "com.dispatchhttp"
ThisBuild / organizationName := "dispatch"

lazy val root = Project(
  "dispatch-all", file(".")
).settings(
  publish := { }
).aggregate(
  core
  // jsoup,
  // tagsoup,
  // liftjson,
  // json4sJackson,
  // json4sNative
)

lazy val core = Project(
  "dispatch-core", file("core")
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
