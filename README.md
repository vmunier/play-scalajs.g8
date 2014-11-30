# Play Framework with Scala.js

This is a simple example application showing how you can integrate a Play project with a Scala.js project.

The application contains three directories:
* `scalajvm` Play application (server side)
* `scalajs` Scala.js application (client side)
* `scala` scala code that you want to share between scalajs and scalajvm (both client and server sides)

## Run the application
```shell
$ sbt
> run
$ open http://localhost:9000
```

## Features

- Run your application like a regular Play app
  - `compile` simply triggers the Scala.js compilation
  - `run` triggers the Scala.js compilation on page refresh
  - `~compile`, `~run`, continuous compilation is also available
  - `start`, `stage` and `dist` generate the optimised javascript
  - the optimised javascript file is [selected](https://github.com/vmunier/play-with-scalajs-example/blob/9624ad45a2350b966bf7b6fab88c6611f3085948/scalajvm/app/views/main.scala.html#L16-L20) when the application runs in prod mode (`start`, `stage`, `dist`)
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown
  - Source maps is _disabled in production_ to prevent your users from seeing the source files. But it can easily be [enabled](https://github.com/vmunier/play-with-scalajs-example/blob/9624ad45a2350b966bf7b6fab88c6611f3085948/scalajvm/app/controllers/SourceMaps.scala#L14-L15) in production too if needed
  - [Two routes](https://github.com/vmunier/play-with-scalajs-example/blob/9624ad45a2350b966bf7b6fab88c6611f3085948/scalajvm/conf/routes#L8-L10) and a [SourceMaps](https://github.com/vmunier/play-with-scalajs-example/blob/9624ad45a2350b966bf7b6fab88c6611f3085948/scalajvm/app/controllers/SourceMaps.scala) controller handle sending the Scala files to the browser. It's the Scala.js plugin that handles generating Source Maps

## IDE integration

### Eclipse

1. `$ sbt eclipse`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder to import the projects

### IntelliJ

1. `$ sbt gen-idea`
2. Inside IntelliJ, `File/Open...`, choose the root folder to import all the projects (do *not* use `Import Project...` or `Import Module...`)
