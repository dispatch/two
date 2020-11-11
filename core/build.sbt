name := "dispatch-core"

description := "Core dispatch module wrapping Java HTTP Client"

enablePlugins(BuildInfoPlugin)

buildInfoKeys := Seq[BuildInfoKey](version)

buildInfoPackage := "dispatch"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % "test"

libraryDependencies += "ws.unfiltered" %% "unfiltered" % "0.10.0-M11" % "test"
libraryDependencies += "ws.unfiltered" %% "unfiltered-netty-server" % "0.10.0-M11" % "test"
