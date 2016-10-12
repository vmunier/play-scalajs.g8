package example

import org.scalajs.dom
import shared.SharedMessages

import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
  }
}
