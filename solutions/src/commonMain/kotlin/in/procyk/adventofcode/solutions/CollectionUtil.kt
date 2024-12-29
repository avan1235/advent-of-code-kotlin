package `in`.procyk.adventofcode.solutions

class DefaultMap<K, V>(
  private val default: V,
  private val map: MutableMap<K, V> = HashMap(),
) : MutableMap<K, V> by map {
  override fun get(key: K): V = map[key] ?: default.also { map[key] = it }
  operator fun plus(kv: Pair<K, V>): DefaultMap<K, V> = (map + kv).toDefaultMap(default)
  override fun toString() = map.toString()
  override fun hashCode() = map.hashCode()
  override fun equals(other: Any?) = map == other
}

fun <K, V> Map<K, V>.toDefaultMap(default: V): DefaultMap<K, V> =
  DefaultMap(default, toMutableMap())

class LazyDefaultMap<K, V>(
  private val default: () -> V,
  private val map: MutableMap<K, V> = HashMap(),
) : MutableMap<K, V> by map {
  override fun get(key: K): V = map[key] ?: default().also { map[key] = it }
  operator fun plus(kv: Pair<K, V>): LazyDefaultMap<K, V> = (map + kv).toLazyDefaultMap(default)
  override fun toString() = map.toString()
  override fun hashCode() = map.hashCode()
  override fun equals(other: Any?) = map == other
}

fun <K, V> Map<K, V>.toLazyDefaultMap(default: () -> V): LazyDefaultMap<K, V> =
  LazyDefaultMap(default, toMutableMap())

fun <U, V> List<U>.groupSeparatedBy(
  separator: (U) -> Boolean,
  includeSeparator: Boolean = false,
  transform: (List<U>) -> V,
): List<V> = sequence {
  var curr = mutableListOf<U>()
  forEach {
    if (separator(it) && curr.isNotEmpty()) yield(transform(curr))
    if (separator(it)) curr = if (includeSeparator) mutableListOf(it) else mutableListOf()
    else curr += it
  }
  if (curr.isNotEmpty()) yield(transform(curr))
}.toList()

inline fun <T, R> Iterable<T>.map2Set(
  destination: MutableSet<R> = LinkedHashSet(),
  transform: (T) -> R,
): MutableSet<R> = destination.apply { for (item in this@map2Set) add(transform(item)) }

fun <T> List<T>.repeat(count: Int): List<T> =
  List(size * count) { this[it % size] }

@Suppress("UNCHECKED_CAST")
class PriorityQueue<T>(size: Int, val comparator: Comparator<T>? = null) : Collection<T> {
  override var size: Int = 0
    private set

  private var arr: Array<T?> = Array<Any?>(size) { null } as Array<T?>

  fun add(element: T) {
    if (size + 1 == arr.size) {
      resize()
    }
    arr[++size] = element
    swim(arr, size, comparator)
  }

  fun peek(): T {
    if (isEmpty()) throw AdventDay.Exception("Priority queue is empty")
    return arr[1]!!
  }

  fun poll(): T {
    if (isEmpty()) throw AdventDay.Exception("Priority queue is empty")
    val res = peek()
    arr.exch(1, size--)
    sink(arr, 1, size, comparator)
    arr[size + 1] = null
    if (isNotEmpty() && (size == (arr.size - 1) / 4)) {
      resize()
    }
    return res
  }

  private fun resize() {
    val old = arr
    arr = Array<Any?>(size * 2) {
      (if (it < size + 1) old[it] else null) as Any?
    } as Array<T?>
  }

  override fun isEmpty(): Boolean {
    return size == 0
  }

  override fun contains(element: T): Boolean {
    for (obj in this) {
      if (obj == element) return true
    }
    return false
  }

  override fun containsAll(elements: Collection<T>): Boolean {
    for (element in elements) {
      if (!contains(element)) return false
    }
    return true
  }

  override fun iterator(): Iterator<T> = arr.copyOfRange(1, size + 1).map { it!! }.iterator()

  companion object {
    private fun <T> greater(arr: Array<T?>, i: Int, j: Int, comparator: Comparator<T>? = null): Boolean {
      if (comparator != null) {
        return comparator.compare(arr[i] as T, arr[j] as T) > 0
      } else {
        val left = arr[i]!! as Comparable<T>
        return left > arr[j]!!
      }
    }

    private fun <T> sink(arr: Array<T?>, a: Int, size: Int, comparator: Comparator<T>? = null) {
      var k = a
      while (2 * k <= size) {
        var j = 2 * k
        if (j < size && greater(arr, j, j + 1, comparator)) j++
        if (!greater(arr, k, j, comparator)) break
        arr.exch(k, j)
        k = j
      }
    }

    private fun <T> swim(arr: Array<T?>, size: Int, comparator: Comparator<T>? = null) {
      var n = size
      while (n > 1 && greater(arr, n / 2, n, comparator)) {
        arr.exch(n, n / 2)
        n /= 2
      }
    }
  }
}

@Suppress("NOTHING_TO_INLINE")
private inline fun <T> Array<T>.exch(i: Int, j: Int) {
  val tmp = this[i]
  this[i] = this[j]
  this[j] = tmp
}
