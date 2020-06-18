# Play Framework with Scala.js

[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vmunier/play-with-scalajs-example?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is a [Giter8](http://www.foundweekends.org/giter8/) template showing how you can integrate a Play project with a Scala.js project.

## Run the application

```shell
$ sbt new vmunier/play-scalajs.g8
$ cd play-scalajs
$ sbt
> project server
> run
$ open http://localhost:9000
```

The application contains three directories:
* `server` Play application (server side)
* `client` Scala.js application (client side)
* `shared` Scala code that you want to share between the server and the client

## Features

The application uses the [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs) sbt plugin and the [scalajs-scripts](https://github.com/vmunier/scalajs-scripts) library.

- Run your application like a regular Play app
  - `compile` triggers the Scala.js `fastOptJS` task
  - `run` triggers the Scala.js `fastOptJS` task on page refresh
  - `~compile`, `~run`, continuous compilation is also available
- Compilation errors from the Scala.js projects are also displayed in the browser
- Set `scalaJSStage` to `FullOptStage` when packaging your application for `fullOptJS` to be executed instead of `fastOptJS`:
  ```
  sbt 'set scalaJSStage in Global := FullOptStage' dist
  ```
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown.
  - Source Maps are enabled in both `fastOptJS` and `fullOptJS` by default. If you wish to disable Source Maps in `fullOptJS`, then add `scalaJSLinkerConfig in (Compile, fullOptJS) ~= (_.withSourceMap(false))` in the Scala.js projects.

## Load the server project at sbt startup

Add the following line to `build.sbt` if you wish to load the server project at sbt startup:
```scala
onLoad in Global := (onLoad in Global).value.andThen(state => "project server" :: state)
```

## Cleaning

The root project aggregates all the other projects by default. Use this root project to clean all the projects at once.
```shell
$ sbt
> clean
```

## IDE integration

### IntelliJ

In IntelliJ, open Project wizard, select `Import Project`, choose the root folder and click `OK`.
Select `Import project from external model` option, choose `SBT project` and click `Next`. Select additional import options and click `Finish`.
Make sure you use the IntelliJ Scala Plugin v2017.2.7 or higher. There are known issues with prior versions of the plugin.

### Eclipse

1. Add `addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")` to `project/plugins.sbt`
2. Add the following lines to the `server`'s settings in `build.sbt`:
```
// Compile the project before generating Eclipse files, so that generated .scala or .class files for Twirl templates are present
EclipseKeys.preTasks := Seq(compile in Compile)
```
3. Run `$ sbt "eclipse with-source=true"`
4. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder. Uncheck the third checkbox to only import client, server and shared/.jvm, click `Finish`. ![Alt text](screenshots/eclipse-play-scalajs.png?raw=true "eclipse play-scalajs screenshot")
