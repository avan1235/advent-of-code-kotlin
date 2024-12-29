package `in`.procyk.adventofcode.solutions

inline fun <T : Any> runIf(c: Boolean, action: () -> T): T? =
  if (c) action() else null

fun <T> T.runIf(condition: Boolean, f: T.() -> T): T =
  if (condition) f() else this

infix fun Int.directedTo(o: Int) = if (this <= o) this..o else this downTo o
