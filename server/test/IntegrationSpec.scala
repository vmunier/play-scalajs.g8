import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {
    "work from within a browser" in new WithDepsBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must contain("shouts out")
    }
  }
}
