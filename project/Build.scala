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
      compile in Compile <<= (compile in Compile) dependsOn Def.task {
        copy((packageJS in (scalajs, Compile)).value, scalajsOutputDir.value)
      },
      resourceGenerators in Compile <+= Def.task {
        copy((packageJS in (scalajs, Compile)).value, scalajsOutputDir.value)
      },
      dist <<= dist dependsOn Def.task {
        copy(Seq( (optimizeJS in (scalajs, Compile)).value ), scalajsOutputDir.value)
      },
      watchSources <++= (sourceDirectory in (scalajs, Compile)).map { path => (path ** "*.scala").get},
      sharedScalaSetting,
      libraryDependencies ++= Seq()
    )

  def copy(scalajsFiles: Seq[File], outputDir: File): Seq[File] = {
    for (inFile <- scalajsFiles) yield {
      val outFile = outputDir / inFile.name
      IO.copyFile(inFile, outFile)
      outFile
    }
    Seq[File]()
  }

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
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
