// Comment to get more information during initialization
logLevel := Level.Warn

// Resolvers
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.url("heroku-sbt-plugin-releases",
  url("https://dl.bintray.com/heroku/sbt-plugins/"))(Resolver.ivyStylePatterns)

// Sbt plugins
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.0")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

// Deployment plugins

addSbtPlugin("com.heroku" % "sbt-heroku" % "0.3.4")
