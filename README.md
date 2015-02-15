# Play Framework with Scala.js

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vmunier/play-with-scalajs-example?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is a simple example application showing how you can integrate a Play project with a Scala.js project.

The application contains three directories:
* `example-server` Play application (server side)
* `example-client` Scala.js application (client side)
* `example-shared` Scala code that you want to share between the server and the client

## Run the application
```shell
$ sbt
> run
$ open http://localhost:9000
```

## Features

The application uses the [sbt-play-scalajs](https://github.com/vmunier/sbt-play-scalajs) sbt plugin and the [play-scalajs-scripts](https://github.com/vmunier/play-scalajs-scripts) library.

- Run your application like a regular Play app
  - `compile` simply triggers the Scala.js compilation
  - `run` triggers the Scala.js fastOptJS command on page refresh
  - `~compile`, `~run`, continuous compilation is also available
  - `start`, `stage` and `dist` generate the optimised javascript
  - [`playscalajs.html.scripts`](https://github.com/vmunier/play-with-scalajs-example/blob/303bfdcf3121d0d45e9425945f7480f84a81ccac/example-server/app/views/main.scala.html#L16) selects the optimised javascript file when the application runs in prod mode (`start`, `stage`, `dist`).
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown
  - Source Maps is _disabled in production_ by default to prevent your users from seeing the source files. But it can easily be enabled in production too by setting `emitSourceMaps in fullOptJS := true` in the Scala.js projects.

## IDE integration

### Eclipse

1. `$ sbt eclipse`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder to import the projects

### IntelliJ

IntelliJ integration does not work well with cross projects for now. The IDEA team is working on a fix.
