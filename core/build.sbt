name := "dispatch-core"

description := "Core dispatch module wrapping Java HTTP Client"

enablePlugins(BuildInfoPlugin)

buildInfoKeys := Seq[BuildInfoKey](version)

buildInfoPackage := "dispatch"
