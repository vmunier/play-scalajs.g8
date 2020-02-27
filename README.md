# Play Framework with Scala.js

[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vmunier/play-with-scalajs-example?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is a [Giter8](http://www.foundweekends.org/giter8/) template showing how you can integrate a Play project with a Scala.js project.

## Run the application

```shell
$ sbt new vmunier/play-scalajs.g8
$ cd play-scalajs
$ sbt
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
  - `compile` triggers the Scala.js fastOptJS command
  - `run` triggers the Scala.js fastOptJS command on page refresh
  - `~compile`, `~run`, continuous compilation is also available
- Compilation errors from the Scala.js projects are also displayed in the browser
- Production archives (e.g. using `stage`, `dist`) contain the optimised javascript
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown
  - Source Maps is _disabled in production_ by default to prevent your users from seeing the source files. But it can easily be enabled in production too by setting `scalaJSLinkerConfig in (Compile, fullOptJS) ~= (_.withSourceMap(true))` in the Scala.js projects.

## Cleaning

The root project aggregates all the other projects by default.
Use this root project, called `play-scalajs` by default, to clean all the projects at once.
```shell
$ sbt
> play-scalajs/clean
```

## IDE integration

### IntelliJ

In IntelliJ, open Project wizard, select `Import Project`, choose the root folder and click `OK`.
Select `Import project from external model` option, choose `SBT project` and click `Next`. Select additional import options and click `Finish`.
Make sure you use the IntelliJ Scala Plugin v2017.2.7 or higher. There are known issues with prior versions of the plugin.

### Eclipse

1. `$ sbt "eclipse with-source=true"`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder. Uncheck the third checkbox to only import client, server and shared/.jvm, click `Finish`. ![Alt text](screenshots/eclipse-play-scalajs.png?raw=true "eclipse play-scalajs screenshot")
