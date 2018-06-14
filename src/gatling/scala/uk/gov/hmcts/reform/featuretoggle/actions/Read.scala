package uk.gov.hmcts.reform.featuretoggle.actions

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.util.ToggleIdStore

object Read {

  private val uidFeeder = Iterator.continually(Map("uid" -> ToggleIdStore.getRandom()))

  val readToggle: ChainBuilder =
    feed(uidFeeder)
      .exec(
        http("Read toggle")
          .get("/api/ff4j/store/features/${uid}")
          .asJSON
          .check(
            status.is(200)
          )
      )
}
