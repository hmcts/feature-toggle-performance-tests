package uk.gov.hmcts.reform.featuretoggle.actions.util

import com.typesafe.config.ConfigFactory

object ToggleIdStore {

  private val totalCount: Int = ConfigFactory.load().getInt("total_toggle_count")
  private val ids = (1 to totalCount).map(i => s"perf-test-$i")

  def getAll() = ids
}
