import sbt._
import Keys._
import play.Play._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

object ApplicationBuild extends Build with UniversalKeys {

  val SharedSrcDir = "scala"
  val PlayStart = "playStart"

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  override def rootProject = Some(scalajvm)

  lazy val scalajvm = Project(
    id = "scalajvm",
    base = file("scalajvm")
  ) enablePlugins (play.PlayScala) settings (scalajvmSettings: _*) aggregate (scalajs)

  lazy val scalajs = Project(
    id = "scalajs",
    base = file("scalajs")
  ) settings (scalajsSettings: _*)

  lazy val sharedScala = Project(
    id = "sharedScala",
    base = file(SharedSrcDir)
  ) settings (sharedScalaSettings: _*)

  lazy val scalajvmSettings =
    Seq(
      name := "play-example",
      version := Versions.app,
      scalaVersion := Versions.scala,
      scalajsOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
      compile in Compile <<= (compile in Compile) dependsOn (fastOptJS in (scalajs, Compile)) dependsOn copySourceMapsTask,
      dist <<= dist dependsOn (fullOptJS in (scalajs, Compile)),
      stage <<= stage dependsOn (fullOptJS in (scalajs, Compile)),
      libraryDependencies ++= Dependencies.scalajvm.value,
      EclipseKeys.skipParents in ThisBuild := false,
      commands ++= Seq(playStartCommand, startCommand)
    ) ++ (
      // ask scalajs project to put its outputs in scalajsOutputDir
      Seq(packageLauncher, fastOptJS, fullOptJS) map { packageJSKey =>
        crossTarget in (scalajs, Compile, packageJSKey) := scalajsOutputDir.value
      }
    ) ++ sharedDirectorySettings

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := "scalajs-example",
      version := Versions.app,
      scalaVersion := Versions.scala,
      persistLauncher := true,
      persistLauncher in Test := false,
      relativeSourceMaps := true,
      libraryDependencies ++= Dependencies.scalajs.value
    ) ++ sharedDirectorySettings

  lazy val sharedScalaSettings =
    Seq(
      name := "shared-scala-example",
      libraryDependencies ++= Dependencies.shared.value
    )

  lazy val sharedDirectorySettings = Seq(
    unmanagedSourceDirectories in Compile += new File((file(".") / SharedSrcDir / "src" / "main" / "scala").getCanonicalPath),
    unmanagedSourceDirectories in Test += new File((file(".") / SharedSrcDir / "src" / "test" / "scala").getCanonicalPath),
    unmanagedResourceDirectories in Compile += file(".") / SharedSrcDir / "src" / "main" / "resources",
    unmanagedResourceDirectories in Test += file(".") / SharedSrcDir / "src" / "test" / "resources"
  )

  val copySourceMapsTask = Def.task {
    val scalaFiles = (Seq(sharedScala.base, scalajs.base) ** ("*.scala")).get
    for (scalaFile <- scalaFiles) {
      val target = new File((classDirectory in Compile).value, scalaFile.getPath)
      IO.copyFile(scalaFile, target)
    }
  }

  // The new 'start' command optimises the JS before calling 'playStart'
  val startCommand = Command.args("start", "<port>") { (state: State, args: Seq[String]) =>
    Project.runTask(fullOptJS in (scalajs, Compile), state)
    state.copy(remainingCommands = s"$PlayStart ${args.mkString(" ")}" +: state.remainingCommands)
  }
  val playStartCommand = Command.make(PlayStart)(play.Play.playStartCommand.parser)
}

object Dependencies {
  val shared = Def.setting(Seq())

  val scalajvm = Def.setting(shared.value ++ Seq(
    "org.webjars" % "jquery" % "1.11.1"
  ))

  val scalajs = Def.setting(shared.value ++ Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalajsDom,
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
  ))
}

object Versions {
  val app = "0.1.0-SNAPSHOT"
  val scala = "2.11.2"
  val scalajsDom = "0.6"
}
