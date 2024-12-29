package `in`.procyk.adventofcode.solutions

import com.ionspin.kotlin.bignum.integer.BigInteger

interface CharSequenceTrie {

  operator fun contains(word: CharSequence): Boolean
}

class MutableCharSequenceTrie private constructor() : CharSequenceTrie {

  private class Node {
    var hasValue: Boolean = false

    private val children: MutableMap<Int, Node> = HashMap()

    operator fun plus(key: Char): Node {
      val code = key.code
      return when (val child = children[code]) {
        null -> Node().also { children.put(code, it) }
        else -> child
      }
    }

    operator fun get(key: Char): Node? = children[key.code]
  }

  private val root: Node = Node()

  operator fun plusAssign(element: CharSequence) {
    if (element.isEmpty()) {
      root.hasValue = true
      return
    }
    element.foldIndexed(root) { idx, currNode, c ->
      (currNode + c).also { if (idx == element.lastIndex) it.hasValue = true }
    }
  }

  override operator fun contains(word: CharSequence): Boolean =
    word.fold(root) { currNode, c -> currNode[c] ?: return false }.hasValue

  companion object {
    private inline fun buildMutableCharSequenceTrie(action: MutableCharSequenceTrie.() -> Unit = {}): MutableCharSequenceTrie =
      MutableCharSequenceTrie().apply(action)

    fun charSequenceTrieOf(vararg elements: CharSequence): CharSequenceTrie =
      buildMutableCharSequenceTrie { elements.forEach { this += it } }
  }
}

inline fun <reified T> String.separated(by: String): List<T> = split(by).map { it.value() }

inline fun <reified T> String.value(): T = when (T::class) {
  String::class -> this as T
  Long::class -> toLongOrNull() as T
  Int::class -> toIntOrNull() as T
  BigInteger::class -> BigInteger.parseString(this, base = 10) as T
  else -> TODO("Add support to read ${T::class.simpleName}")
}

fun List<String>.groupSeparatedByBlankLine(): List<List<String>> =
  groupSeparatedBy({ it.isBlank() }) { it }

val List<String>.size2D: V2
  get() {
    val sizeX = map2Set { it.length }.single()
    val sizeY = size
    return sizeX xy sizeY
  }
