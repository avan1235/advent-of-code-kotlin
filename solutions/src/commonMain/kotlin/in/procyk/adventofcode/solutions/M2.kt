package `in`.procyk.adventofcode.solutions

import kotlin.jvm.JvmInline

@JvmInline
value class M2<T : Any>(val data: List<List<T>>) {
  val size2D: V2
    get() {
      val sizeX = data.map2Set { it.size }.single()
      val sizeY = data.size
      return sizeX xy sizeY
    }

  operator fun get(v: V2): T? = data.getOrNull(v.y)?.getOrNull(v.x)

  fun transpose(): M2<T> = M2(data.transpose())

  companion object {
    operator fun invoke(data: List<String>): M2<Char> = M2(data.map(String::toList))
  }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
  val n = map { it.size }.toSet().singleOrNull() ?: throw AdventDay.Exception("Invalid data to transpose: $this")
  return List(n) { y -> List(size) { x -> this[x][y] } }
}
