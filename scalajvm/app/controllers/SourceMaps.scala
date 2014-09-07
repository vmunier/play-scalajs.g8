package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.iteratee.Enumerator
import scala.concurrent.ExecutionContext.Implicits.global

object SourceMaps extends Controller {

  def scalaFile(path: String) = Action { request =>
    Play.resourceAsStream(request.path).map { streamedFile =>
      Ok.chunked(Enumerator.fromStream(streamedFile))
    }.getOrElse {
      NotFound(s"File ${request.path} not found")
    }
  }

}
