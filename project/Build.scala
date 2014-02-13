import sbt._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

object ApplicationBuild extends Build {

  lazy val root = Project(
    id   = "root",
    base = file(".")
  ) aggregate (scalajvm, scalajs)

  lazy val scalajvm = play.Project(
    name = "scalajvm",
    path = file("scalajvm")
  ) settings (scalajvmSettings: _*)

  lazy val scalajs = Project(
    id   = "scalajs",
    base = file("scalajs")
  ) settings (scalajsSettings: _*)

  lazy val scalajvmSettings =
    play.Project.playScalaSettings ++ Seq(
      name                 := "play-example",
      version              := "0.1.0-SNAPSHOT",
      playExternalAssets ++= Seq(((crossTarget in (scalajs, packageJS)).value, (base: File) => base ** "*.js", "public/scalajs")),
      sharedScalaSetting,
      libraryDependencies ++= Seq()
    )

  lazy val scalajsSettings =
    scalaJSSettings ++
    Seq(
      name := "example",
      version := "0.1.0-SNAPSHOT",
      // Specify additional .js file to be passed to package-js and optimize-js
      unmanagedSources in (Compile, ScalaJSKeys.packageJS) += baseDirectory.value / "js" / "startup.js",
      sharedScalaSetting,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.1-SNAPSHOT"
      )
    )

  lazy val sharedScalaSetting = unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "scala"
}
