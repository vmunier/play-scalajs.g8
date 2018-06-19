// Comment to get more information during initialization
logLevel := Level.Warn

// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// Sbt plugins
$if(useScalaJS1x.truthy)$
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.0.0-M3")
$else$
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8-0.6")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "0.6.23")
$endif$

addSbtPlugin("com.typesafe.play"         % "sbt-plugin"                % "2.6.15")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "0.5.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-gzip"                  % "1.0.2")
addSbtPlugin("com.typesafe.sbt"          % "sbt-digest"                % "1.1.4")
addSbtPlugin("com.typesafe.sbteclipse"   % "sbteclipse-plugin"         % "5.2.4")
