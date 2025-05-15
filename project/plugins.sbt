ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

// needed for the tests (ci/checksourcemaps.sh)
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8" % "0.17.0")
// needed to make sbt-giter8 work with SBT >= v1.2.x
libraryDependencies += { "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value }

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.3.0")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.19.0")
addSbtPlugin("org.playframework"         % "sbt-plugin"                % "3.0.7")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.3.2")
addSbtPlugin("com.github.sbt"            % "sbt-gzip"                  % "2.0.0")
addSbtPlugin("com.github.sbt"            % "sbt-digest"                % "2.1.0")
