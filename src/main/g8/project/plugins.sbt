// Comment to get more information during initialization
logLevel := Level.Warn

addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.2.0")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.8.0")
addSbtPlugin("com.typesafe.play"         % "sbt-plugin"                % "2.8.11")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.1.0")
addSbtPlugin("com.typesafe.sbt"          % "sbt-gzip"                  % "1.0.2")
addSbtPlugin("com.typesafe.sbt"          % "sbt-digest"                % "1.1.4")
