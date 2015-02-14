import sbt._
import Keys._
import play.PlayScala
import play.Play._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

object ApplicationBuild extends Build with UniversalKeys {

  val SharedSrcDir = "shared"

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  override def rootProject = Some(jvm)

  lazy val jvm = Project(
    id = "jvm",
    base = file("jvm")
  ) enablePlugins (PlayScala) settings (jvmSettings: _*) aggregate (js)

  lazy val js = Project(
    id = "js",
    base = file("js")
  ) enablePlugins(ScalaJSPlugin) settings (jsSettings: _*)

  lazy val sharedScala = Project(
    id = "sharedScala",
    base = file(SharedSrcDir)
  ) settings (sharedScalaSettings: _*)

  lazy val jvmSettings =
    Seq(
      version := Versions.app,
      scalaVersion := Versions.scala,
      scalajsOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
      compile in Compile <<= (compile in Compile) dependsOn (fastOptJS in (js, Compile)) dependsOn copySourceMapsTask,
      dist <<= dist dependsOn (fullOptJS in (js, Compile)),
      stage <<= stage dependsOn (fullOptJS in (js, Compile)),
      libraryDependencies ++= Dependencies.jvm.value,
      EclipseKeys.skipParents in ThisBuild := false
    ) ++ (
      // ask scalajs project to put its outputs in scalajsOutputDir
      Seq(packageScalaJSLauncher, fastOptJS, fullOptJS) map { packageJSKey =>
        crossTarget in (js, Compile, packageJSKey) := scalajsOutputDir.value
      }
    ) ++ sharedDirectorySettings

  lazy val jsSettings =
    Seq(
      version := Versions.app,
      scalaVersion := Versions.scala,
      persistLauncher := true,
      persistLauncher in Test := false,
      relativeSourceMaps := true,
      libraryDependencies ++= Dependencies.js.value
    ) ++ sharedDirectorySettings

  lazy val sharedScalaSettings =
    Seq(
      name := "shared",
      libraryDependencies ++= Dependencies.shared.value
    )

  lazy val sharedDirectorySettings = Seq(
    unmanagedSourceDirectories in Compile += new File((file(".") / SharedSrcDir / "src" / "main" / "scala").getCanonicalPath),
    unmanagedSourceDirectories in Test += new File((file(".") / SharedSrcDir / "src" / "test" / "scala").getCanonicalPath),
    unmanagedResourceDirectories in Compile += file(".") / SharedSrcDir / "src" / "main" / "resources",
    unmanagedResourceDirectories in Test += file(".") / SharedSrcDir / "src" / "test" / "resources"
  )

  val copySourceMapsTask = Def.task {
    val scalaFiles = (Seq(sharedScala.base, js.base) ** ("*.scala")).get
    for (scalaFile <- scalaFiles) {
      val target = new File((classDirectory in Compile).value, scalaFile.getPath)
      IO.copyFile(scalaFile, target)
    }
  }
}

object Dependencies {
  val shared = Def.setting(Seq())

  val jvm = Def.setting(shared.value ++ Seq(
    "com.vmunier" %% "play-scalajs-sourcemaps" % Versions.playScalajsSourcemaps,
    "com.vmunier" %% "play-scalajs-scripts" % Versions.playScalajsScripts,
    "org.webjars" % "jquery" % Versions.jquery
  ))

  val js = Def.setting(shared.value ++ Seq(
    "org.scala-js" %%% "scalajs-dom" % Versions.scalajsDom
  ))
}

object Versions {
  val app = "0.1.0-SNAPSHOT"
  val scala = "2.11.5"
  val scalajsDom = "0.8.0"
  val jquery = "1.11.1"
  val playScalajsSourcemaps = "0.1.0"
  val playScalajsScripts = "0.1.0"
}
