package uk.gov.hmcts.reform.featuretoggle.actions

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.util.ToggleIdStore

object Delete {

  private val config: Config = ConfigFactory.load()

  val delete: ChainBuilder =
    feed(ToggleIdStore.getAll().map(id => Map("deleteId" -> id)))
      .exec(
        http("Delete toggle")
          .delete("/api/ff4j/store/features/${deleteId}")
          .basicAuth(
            config.getString("username"),
            config.getString("password")
          )
          .check(
            status.is(204)
          )
      )
}
