package `in`.procyk.adventofcode.solutions

import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign


data class V2(val x: Int, val y: Int) {
  override fun toString() = "($x,$y)"
}

infix fun Int.xy(i: Int): V2 = V2(this, i)

operator fun V2.unaryMinus(): V2 = -x xy -y

operator fun V2.plus(other: V2): V2 = x + other.x xy y + other.y

operator fun V2.minus(other: V2): V2 = x - other.x xy y - other.y

operator fun V2.rem(v: Int): V2 = x % v xy y % v

operator fun V2.rem(v: V2): V2 = x % v.x xy y % v.y

fun V2.mod(v: Int): V2 = x.mod(v) xy y.mod(v)

fun V2.mod(v: V2): V2 = x.mod(v.x) xy y.mod(v.y)

operator fun Int.times(other: V2): V2 = other.x * this xy other.y * this

val V2.length: Long get() = x.toLong() * x.toLong() + y.toLong() * y.toLong()

val V2.abs: V2 get() = abs(x) xy abs(y)

val V2.normalized: V2 get() = x.sign * min(1, abs(x)) xy y.sign * min(1, abs(y))

fun Char.toMove(): V2 = when (this) {
  '>' -> 1 xy 0
  '<' -> -1 xy 0
  '^' -> 0 xy -1
  'v' -> 0 xy 1
  else -> error("unknown move char $this")
}

enum class Dir(val v: V2) {
  N(0 xy 1), E(1 xy 0), S(0 xy -1), W(-1 xy 0);

  val reversed: Dir
    get() = when (this) {
      N -> S; E -> W; S -> N; W -> E
    }
}
