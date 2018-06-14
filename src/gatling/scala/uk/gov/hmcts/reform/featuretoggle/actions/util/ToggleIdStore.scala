package uk.gov.hmcts.reform.featuretoggle.actions.util

import java.util.UUID

import scala.collection.mutable
import scala.util.Random

object ToggleIdStore {

  private val ids = mutable.SortedSet[String]()

  def addRandom() = {
    val id = "perf-test-" + UUID.randomUUID.toString
    ids.add(id)
    id
  }
  def getRandom() = ids.toVector((new Random).nextInt(ids.size))
}
