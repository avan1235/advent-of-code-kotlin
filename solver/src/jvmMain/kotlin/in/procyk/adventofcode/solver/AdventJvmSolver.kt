package `in`.procyk.adventofcode.solver

import androidx.compose.ui.window.singleWindowApplication
import `in`.procyk.adventofcode.solutions.Advent

fun adventJvmSolver(advent: Advent) {
  singleWindowApplication {
    AdventSolver(
      advent = advent,
      onDaySelected = { selectedDay ->
        window.title = "Day ${selectedDay.n} - Advent of Code ${advent.year} | Solver"
      }
    )
  }
}
