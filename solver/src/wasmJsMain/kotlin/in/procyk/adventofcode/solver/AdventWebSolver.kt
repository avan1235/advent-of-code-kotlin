package `in`.procyk.adventofcode.solver

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import `in`.procyk.adventofcode.solutions.Advent
import kotlinx.browser.document


@OptIn(ExperimentalComposeUiApi::class)
fun adventWebSolver(advent: Advent) {
  ComposeViewport(document.body!!) {
    AdventSolver(
      advent = advent,
      onDaySelected = { selectedDay ->
        document.title = "Day ${selectedDay.n} - Advent of Code ${advent.year} | Solver"
      }
    )
  }
}
