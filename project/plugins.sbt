// needed for the tests (ci/checksourcemaps.sh)
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8" % "0.11.0")
// needed to make sbt-giter8 work with SBT >= v1.2.x
libraryDependencies += { "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value }
