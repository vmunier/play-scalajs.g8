# Play Framework with Scala.js

This is a simple example application showing how you can integrate a Play project with a Scala.js project.

The application contains three directories:
* `scalajvm` Play application (server side)
* `scalajs` Scala.js application (client side)
* `scala` scala code that you want to share between scalajs and scalajvm (both client and server sides)

## Run the application
```shell
$ sbt
> project scalajvm
> ~run
$ open http://localhost:9000
```

## Notes
* `packageJS` is triggered when compiling (works with `compile`, `~compile`, `~run`)
* `optimizeJS` is triggered when calling Play `dist`
* The ScalaJS output goes to scalajvm/public/javascripts/scalajs
* scalajvm/public/javascripts/scalajs is present in .gitignore
* Customize the Scala.js output directory by updating the `scalajsOutputDir` setting in project/Build.scala

## Possible Improvements
* `packageJS` should be triggered on Browser's Refresh when using the Play `run` command.<br>
For the moment, use `~run` in place of `run`.
