name := "play-scalajs.g8"

enablePlugins(ScriptedPlugin)

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "1.1.0"
)
