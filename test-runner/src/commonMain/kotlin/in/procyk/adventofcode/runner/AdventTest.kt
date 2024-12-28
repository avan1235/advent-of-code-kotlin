package `in`.procyk.adventofcode.runner

import `in`.procyk.adventofcode.solutions.AdventDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals

abstract class AdventTest {

  protected fun testAdventDay(
    day: AdventDay,
    part1: String? = null,
    part2: String? = null,
    reader: AdventDay.InputReader = FileAdventInputReader()
  ) {
    runBlocking(Dispatchers.Default) {
      val solution = day.solve(with = reader)
      assertEquals(part1, solution.part1, "Day ${day.n} part 1 output is not as expected")
      assertEquals(part2, solution.part2, "Day ${day.n} part 2 output is not as expected")
    }
  }
}
