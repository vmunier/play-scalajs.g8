import sbt._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys

object ApplicationBuild extends Build with UniversalKeys {

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

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
      scalajsOutputDir     := baseDirectory.value / "public" / "javascripts" / "scalajs",
      compile in Compile <<= (compile in Compile) dependsOn (packageJS in (scalajs, Compile)),
      resourceGenerators in Compile <+= Def.task {
        val _ = (packageJS in (scalajs, Compile)).value
        Seq[File]()
      },
      dist <<= dist dependsOn (optimizeJS in (scalajs, Compile)),
      watchSources <++= (sourceDirectory in (scalajs, Compile)).map { path => (path ** "*.scala").get},
      sharedScalaSetting,
      libraryDependencies ++= Seq()
    ) ++ (
      // ask scalajs project to put its outputs in scalajsOutputDir
      Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, optimizeJS) map {
        packageJSKey =>
          crossTarget in (scalajs, Compile, packageJSKey) := scalajsOutputDir.value
      }
    )

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := "scalajs-example",
      version := "0.1.0-SNAPSHOT",
      // Specify additional .js file to be passed to package-js and optimize-js
      unmanagedSources in (Compile, ScalaJSKeys.packageJS) += baseDirectory.value / "js" / "startup.js",
      sharedScalaSetting,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.3-SNAPSHOT"
      )
    )

  lazy val sharedScalaSetting = unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "scala"
}
