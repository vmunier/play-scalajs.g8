import sbt._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

object ApplicationBuild extends Build with UniversalKeys {

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  override def rootProject = Some(scalajvm)

  val sharedSrcDir = "scala"

  lazy val scalajvm = play.Project(
    name = "scalajvm",
    path = file("scalajvm")
  ) settings (scalajvmSettings: _*) aggregate (scalajs)

  lazy val scalajs = Project(
    id   = "scalajs",
    base = file("scalajs")
  ) settings (scalajsSettings: _*)

  lazy val sharedScala = Project(
    id = "sharedScala",
    base = file(sharedSrcDir)
  ) settings(sharedScalaSettings: _*)

  lazy val scalajvmSettings =
    play.Project.playScalaSettings ++ Seq(
      name                 := "play-example",
      version              := "0.1.0-SNAPSHOT",
      scalajsOutputDir     := (crossTarget in Compile).value / "classes" / "public" / "javascripts",
      compile in Compile <<= (compile in Compile) dependsOn (preoptimizeJS in (scalajs, Compile)),
      dist <<= dist dependsOn (optimizeJS in (scalajs, Compile)),
      addSharedSrcSetting,
      libraryDependencies ++= Seq(),
      EclipseKeys.skipParents in ThisBuild := false
    ) ++ (
      // ask scalajs project to put its outputs in scalajsOutputDir
      Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, preoptimizeJS, optimizeJS) map { packageJSKey =>
        crossTarget in (scalajs, Compile, packageJSKey) := scalajsOutputDir.value
      }
    )

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := "scalajs-example",
      version := "0.1.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.3-SNAPSHOT"
      ),
      addSharedSrcSetting
    )

  lazy val sharedScalaSettings =
    Seq(
      name := "shared-scala-example",
      scalaSource in Compile := baseDirectory.value,
      EclipseKeys.skipProject := true
    )

  lazy val addSharedSrcSetting = unmanagedSourceDirectories in Compile += new File((baseDirectory.value / ".." / sharedSrcDir).getCanonicalPath)
}
