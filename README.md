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

## Notes
* `preoptimizeJS` is triggered when compiling (works with `compile`, `~compile`, `run`, `~run`)
* `optimizeJS` is triggered when calling Play `dist`

## IDE integration

### Eclipse

1. `$ sbt eclipse`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder to import the projects

### IntelliJ

1. `$ sbt idea`
2. Inside IntelliJ, `File/Open...`, choose the root folder to import all the projects
