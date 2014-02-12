package controllers

import play.api._
import play.api.mvc._
import shared.Greetings

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(Greetings.itWorks))
  }

}
