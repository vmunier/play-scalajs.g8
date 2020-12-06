name := "play-scalajs.g8"

enablePlugins(ScriptedPlugin)

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
libraryDependencies ++= Seq(
  "com.vmunier" %% "scalajs-scripts" % "1.1.4",
  "org.scala-js" %%% "scalajs-dom" % "1.1.0"
)
