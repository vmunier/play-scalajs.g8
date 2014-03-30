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
2. Inside Eclipse, import the application into your Workspace with `File/Import/General/Existing project...`

### IntelliJ

The sbt `eclipse` command is used because it works better than the `gen-idea` command for this particular project.

1. `$ sbt eclipse`
2. Inside IntelliJ, `File/Import Project...`, choose `Import project from external model` and then select `Eclipse`
