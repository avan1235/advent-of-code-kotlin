package `in`.procyk.adventofcode.solver

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import `in`.procyk.adventofcode.solutions.runIf

@Composable
internal fun <T> AdventDropdown(
  preselected: T,
  options: List<T>,
  representation: (T) -> String,
  onOptionSelected: (T) -> Unit = {},
  modifier: Modifier = Modifier,
  offset: DpOffset = DpOffset.Zero,
  scrollState: ScrollState = rememberScrollState(),
  properties: PopupProperties = remember { PopupProperties() },
) {
  var expanded by remember { mutableStateOf(false) }
  var selectedOption by remember { mutableStateOf(preselected) }

  Box(
    contentAlignment = Alignment.CenterStart,
    modifier = modifier
      .height(40.dp)
      .clip(RectangleShape)
      .border(BorderStroke(1.dp, AdventWhite), RectangleShape)
      .runIf(!expanded) { clickable { expanded = true } },
  ) {
    Text(
      text = representation(selectedOption),
      fontSize = 14.sp,
      lineHeight = 14.sp,
      modifier = Modifier
        .padding(start = 16.dp, end = 40.dp)
    )
    val degree by animateFloatAsState(if (expanded) 180f else 0f)
    Icon(
      imageVector = Icons.Filled.ArrowDropDown,
      contentDescription = "open dropdown",
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 8.dp)
        .rotate(degree)
    )
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      modifier = modifier,
      offset = offset,
      scrollState = scrollState,
      properties = properties,
    ) {
      options.forEach { selectionOption ->
        DropdownMenuItem(
          modifier = Modifier
            .height(40.dp),
          onClick = {
            selectedOption = selectionOption
            expanded = false
            onOptionSelected(selectionOption)
          },
          text = {
            Text(text = representation(selectionOption))
          }
        )
      }
    }
  }
}
