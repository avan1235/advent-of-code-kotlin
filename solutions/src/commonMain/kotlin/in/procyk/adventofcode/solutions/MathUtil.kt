package `in`.procyk.adventofcode.solutions

import com.ionspin.kotlin.bignum.integer.BigInteger

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun lcm(a: Int, b: Int): Int = a / gcd(a, b) * b

tailrec fun gcd(a: BigInteger, b: BigInteger): BigInteger = if (b == BigInteger.ZERO) a else gcd(b, a % b)

fun lcm(a: BigInteger, b: BigInteger): BigInteger = a / gcd(a, b) * b

fun Iterable<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { acc, i -> acc + i }
