package uk.gov.hmcts.reform.featuretoggle.actions


import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.featuretoggle.actions.util.ToggleIdStore

object Create {

  private val config: Config = ConfigFactory.load()

  val createToggle: ChainBuilder =
    feed(ToggleIdStore.getAll().map(uid => Map("uid" -> uid)))
      .exec(
        http("Create toggle")
          .put("/api/ff4j/store/features/${uid}")
          .asJSON
          .basicAuth(
            config.getString("username"),
            config.getString("password")
          )
          .body(
            StringBody(
              """
                |{
                |  "uid": "${uid}",
                |  "description": "Feature toggle for performance test",
                |  "enable": true
                |}
              """.stripMargin
            )
          )
          .check(
            status.is(201)
          )
      )
}
