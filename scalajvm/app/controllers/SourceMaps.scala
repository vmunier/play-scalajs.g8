package controllers

import play.api.Play.current
import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

object SourceMaps extends Controller {

  def scalaFile(path: String) = Action { request =>
    Play.resourceAsStream(request.path) match {
      // delete the if clause to enable source map in production too
      case Some(streamedFile) if Play.isDev(Play.current) =>
        Ok.chunked(Enumerator.fromStream(streamedFile))
      case _ =>
        NotFound(s"File ${request.path} not found")
    }
  }

}
