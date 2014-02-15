package controllers

import play.api._
import play.api.mvc._
import shared.SharedMessages

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

}
