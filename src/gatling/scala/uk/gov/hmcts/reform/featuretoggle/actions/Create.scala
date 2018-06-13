package uk.gov.hmcts.reform.featuretoggle.actions


import java.util.UUID

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object Create {

  private val config: Config = ConfigFactory.load()
  private val uidFeeder = Iterator.continually(Map("uid" -> ("perf-test-" + UUID.randomUUID.toString)))

  val createToggle: ChainBuilder =
    feed(uidFeeder)
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
