package `in`.procyk.adventofcode.solutions

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED

abstract class AdventDay(val n: Int) : Comparable<AdventDay> {

  protected abstract suspend fun SolveContext.solve(lines: List<String>)

  suspend fun solve(with: InputReader): Solution =
    SolveContext().use { with(it) { solve(with) } }

  suspend fun SolveContext.solve(with: InputReader): Solution {
    val lines = with.readInput(this@AdventDay).trim().lines()
    solve(lines)
    return Solution(part1, part2)
  }

  suspend fun solve(input: String): Solution =
    SolveContext().use { with(it) { solve(input) } }

  suspend fun SolveContext.solve(input: String): Solution =
    solve(with = { input })

  override fun compareTo(other: AdventDay): Int =
    n.compareTo(other.n)

  fun interface InputReader {

    fun readInput(day: AdventDay): String
  }

  class Exception(override val message: String) : kotlin.Exception(message)

  data class Solution(val part1: String?, val part2: String?)

  inner class SolveContext(
    private val debug: Channel<String> = Channel(capacity = UNLIMITED)
  ) : AutoCloseable {

    var part1: String? = null
      private set
    var part2: String? = null
      private set

    override fun close() {
      debug.close()
    }

    suspend fun <T> T.printIt(): T = apply {
      debug.send(toString())
    }

    suspend fun <T> T.part1(): T = apply {
      val solution = toString()
      part1 = solution
      "Day $n Part 1: $solution".printIt()
    }

    suspend fun <T> T.part2(): T = apply {
      val solution = toString()
      part2 = solution
      "Day $n Part 2: $solution".printIt()
    }

    fun <T : Any> T?.notNull(message: String): T =
      this ?: throw Exception(message)
  }
}
