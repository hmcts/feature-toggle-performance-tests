package uk.gov.hmcts.reform.featuretoggle

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.Create.createToggle
import uk.gov.hmcts.reform.featuretoggle.actions.Read.readToggle
import uk.gov.hmcts.reform.featuretoggle.actions.Delete.delete

import scala.concurrent.duration._

class MainSimulation extends Simulation {

  val config: Config = ConfigFactory.load()

  private val totalToggles = config.getInt("total_toggle_count")
  private val readDuration: FiniteDuration = 10.minutes
  private val readDelay: FiniteDuration = 10.seconds
  private val deleteDelay: FiniteDuration = readDelay + readDuration + 15.seconds

  setUp(
    scenario("Create toggles")
      .repeat(totalToggles) {
        exec(createToggle)
      }
      .inject(atOnceUsers(1)),
    scenario("Read toggles")
      .during(readDuration)(
        exec(
          readToggle
          pause(1.seconds, 2.seconds)
        )
      )
      .inject(
        nothingFor(readDelay), // wait fro previous scenario to end
        rampUsers(100).over(10.seconds)
      ),
    scenario("Delete toggles")
      .repeat(totalToggles) {
        exec(delete)
      }
      .inject(
        nothingFor(deleteDelay),
        atOnceUsers(1)
      )
  ).protocols(http.baseURL(config.getString("baseUrl")))
}
