import play.api.test.{WithApplicationLoader, WithBrowser}

class WithDepsApplication() extends WithApplicationLoader(new ExampleApplicationLoader())

class WithDepsBrowser() extends WithBrowser(app = new WithDepsApplication().app)
