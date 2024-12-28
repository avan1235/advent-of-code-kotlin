package `in`.procyk.adventofcode.runner

import `in`.procyk.adventofcode.solutions.AdventDay
import kotlinx.io.buffered
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteString

class FileAdventInputReader(
  private val base: String = "input",
) : AdventDay.InputReader {

  override fun readInput(day: AdventDay): String = SystemFileSystem.run {
    val inputPath = Path(base, "Day${day.n}.in")
    return when {
      !exists(inputPath) -> error("Input file $inputPath does not exist")
      else -> source(inputPath).use { source ->
        source.buffered().readByteString()
      }.decodeToString()
    }
  }
}
