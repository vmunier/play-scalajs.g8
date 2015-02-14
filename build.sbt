import sbt.Project.projectToRef

lazy val jsProjects = Seq(js)
lazy val scalaV = "2.11.5"

lazy val jvm = project.settings(
  scalaVersion := scalaV,
  scalaJSProjects := jsProjects,
  pipelineStages := Seq(scalaJSProd, gzip),
  libraryDependencies ++= Seq(
    "com.vmunier" %% "play-scalajs-scripts" % "0.1.0",
    "org.webjars" % "jquery" % "1.11.1"
  ),
  EclipseKeys.skipParents in ThisBuild := false).
  enablePlugins(PlayScala).
  aggregate(jsProjects.map(projectToRef): _*).
  dependsOn(sharedJvm)

lazy val js = project.settings(
  scalaVersion := scalaV,
  persistLauncher := true,
  persistLauncher in Test := false,
  sourceMapsDirectories += sharedJs.base / "..",
  unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0").
  enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedJs)

lazy val shared = crossProject.crossType(CrossType.Pure).
  settings(scalaVersion := scalaV).
  jsConfigure(_ enablePlugins ScalaJSPlay).
  jsSettings(sourceMapsBase := baseDirectory.value / "..")

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the jvm project at sbt startup
onLoad in Global := (Command.process("project jvm", _: State)) compose (onLoad in Global).value
