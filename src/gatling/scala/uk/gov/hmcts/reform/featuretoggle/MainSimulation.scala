package uk.gov.hmcts.reform.featuretoggle

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.Create.createToggle

import scala.concurrent.duration._

class MainSimulation extends Simulation {

  val config: Config = ConfigFactory.load()

  setUp(
    scenario("Main scenario")
      .during(1.minutes)(
        exec(
          createToggle
          pause(1.seconds, 2.seconds)
        )
      )
      .inject(
        rampUsers(100).over(10.seconds)
      )
  ).protocols(http.baseURL(config.getString("baseUrl")))
}
