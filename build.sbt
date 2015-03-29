import sbt.Project.projectToRef

lazy val clients = Seq(exampleClient)
lazy val scalaV = "2.11.5"

lazy val exampleServer = (project in file("example-server")).settings(
  scalaVersion := scalaV,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  libraryDependencies ++= Seq(
    "com.vmunier" %% "play-scalajs-scripts" % "0.1.0",
    "org.webjars" % "jquery" % "1.11.1"
  ),
  // Heroku specific
  herokuAppName in Compile := "your-heroku-app-name",
  herokuSkipSubProjects in Compile := false,
  // for Eclipse users
  EclipseKeys.skipParents in ThisBuild := false
).enablePlugins(PlayScala).
  aggregate(clients.map(projectToRef): _*).
  dependsOn(exampleSharedJvm)

lazy val exampleClient = (project in file("example-client")).settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  sourceMapsDirectories += exampleSharedJs.base / "..",
  unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(exampleSharedJs)

lazy val exampleShared = (crossProject.crossType(CrossType.Pure) in file("example-shared")).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSPlay).
  jsSettings(sourceMapsBase := baseDirectory.value / "..")

lazy val exampleSharedJvm = exampleShared.jvm
lazy val exampleSharedJs = exampleShared.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project exampleServer", _: State)) compose (onLoad in Global).value
