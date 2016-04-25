import controllers.{Application, Assets}
import play.api.ApplicationLoader.Context
import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import router.Routes

class ExampleApplicationLoader() extends ApplicationLoader {
  def load(context: Context) = new ApplicationComponents(context).application
}

class ApplicationComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val applicationController = new Application()(environment)
  lazy val assets = new Assets(httpErrorHandler)
  override lazy val router = new Routes(httpErrorHandler, applicationController, assets)
}