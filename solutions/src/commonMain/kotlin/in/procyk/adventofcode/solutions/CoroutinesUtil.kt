package `in`.procyk.adventofcode.solutions

import com.ionspin.kotlin.bignum.integer.BigInteger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <T, U> Iterable<T>.parallelMap(selector: suspend (T) -> U): List<U> = coroutineScope {
  map { async { selector(it) } }.awaitAll()
}

suspend fun <T> Iterable<T>.parallelFilter(selector: suspend (T) -> Boolean): List<T> =
  parallelMap { it to selector(it) }.filter { it.second }.map { it.first }

suspend fun <T> Iterable<T>.parallelCount(selector: suspend (T) -> Boolean): BigInteger =
  parallelMap { selector(it) }.map { if (it) BigInteger.ONE else BigInteger.ZERO }.sum()
