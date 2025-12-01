package `in`.procyk.adventofcode.solutions

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

abstract class Advent {
  abstract val year: Int
  abstract val days: List<AdventDay>

  @OptIn(ExperimentalTime::class)
  fun today(now: Instant = Clock.System.now()): AdventDay? {
    val localDateTime = now.toLocalDateTime(TimeZone.of("UTC-5"))
    if (localDateTime.year != year) return null
    if (localDateTime.month.number != 12) return null
    return days.firstOrNull { it.n == localDateTime.day }
  }
}
