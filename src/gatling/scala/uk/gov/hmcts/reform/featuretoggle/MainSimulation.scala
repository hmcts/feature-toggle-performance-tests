package uk.gov.hmcts.reform.featuretoggle

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.Create.createToggle
import uk.gov.hmcts.reform.featuretoggle.actions.Read.readToggle

import scala.concurrent.duration._

class MainSimulation extends Simulation {

  val config: Config = ConfigFactory.load()

  setUp(
    scenario("Create toggles")
      .repeat(100) {
        exec(createToggle)
      }
      .inject(atOnceUsers(1)),
    scenario("Read toggles")
      .during(1.minutes)(
        exec(
          readToggle
            pause(1.seconds, 2.seconds)
        )
      )
      .inject(
        nothingFor(10.seconds), // wait fro previous scenario to end
        rampUsers(100).over(10.seconds)
      )
  ).protocols(http.baseURL(config.getString("baseUrl")))
}
