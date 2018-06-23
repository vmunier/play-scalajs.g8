// Comment to get more information during initialization
logLevel := Level.Warn

// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// Sbt plugins

// Use Scala.js 1.x
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.0.0-M3")
// If you prefer using Scala.js 0.6.x, uncomment the following plugins instead:
// addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8-0.6")
// addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "0.6.23")

addSbtPlugin("com.typesafe.play"         % "sbt-plugin"                % "2.6.15")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "0.5.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-gzip"                  % "1.0.2")
addSbtPlugin("com.typesafe.sbt"          % "sbt-digest"                % "1.1.4")
addSbtPlugin("com.typesafe.sbteclipse"   % "sbteclipse-plugin"         % "5.2.4")
