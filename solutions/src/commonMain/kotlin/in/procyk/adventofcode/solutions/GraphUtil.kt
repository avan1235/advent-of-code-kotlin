package `in`.procyk.adventofcode.solutions

import com.ionspin.kotlin.bignum.integer.BigInteger

interface Graph<Node> {
  enum class SearchType { DFS, BFS }

  val nodes: Sequence<Node>

  fun neighbours(node: Node): Sequence<Node>

  fun search(
    from: Node,
    type: SearchType = SearchType.DFS,
    checkIfVisited: Boolean = true,
    checkIfOnQueue: Boolean = false,
    visit: (from: Node, to: Node, toDistance: Int) -> Boolean = { _, _, _ -> true },
    action: (node: Node, distance: Int) -> Unit = { _, _ -> },
  ): Set<Node> {
    data class NodeAtDistance(val node: Node, val distance: Int)

    val visited = mutableSetOf<Node>()
    val onQueue = mutableSetOf<NodeAtDistance>()
    val queue = ArrayDeque<NodeAtDistance>()
    tailrec fun go(curr: NodeAtDistance) {
      onQueue -= curr
      visited += curr.also { action(it.node, it.distance) }.node
      neighbours(curr.node).forEach {
        if (checkIfVisited && it in visited) return@forEach
        if (!visit(curr.node, it, curr.distance + 1)) return@forEach
        val next = NodeAtDistance(it, curr.distance + 1)
        if (checkIfOnQueue && next in onQueue) return@forEach

        onQueue += next
        queue += next
      }
      when (type) {
        SearchType.DFS -> go(queue.removeLastOrNull() ?: return)
        SearchType.BFS -> go(queue.removeFirstOrNull() ?: return)
      }
    }
    return visited.also { go(NodeAtDistance(from, 0)) }
  }
}

fun <Node> Map<Node, Set<Node>>.topologicalSort(): List<Node> = let { graph ->
  val inCount = DefaultMap<Node, Int>(0)
  for ((vertex, neighbours) in graph) {
    if (vertex !in inCount) {
      inCount[vertex] = 0
    }
    for (n in neighbours) {
      inCount[n] += 1
    }
  }

  val queue = ArrayDeque<Node>()
  for ((vertex, edges) in inCount) {
    if (edges == 0) {
      queue += vertex
    }
  }

  val result = mutableListOf<Node>()

  while (true) {
    val vertex = queue.removeFirstOrNull() ?: break
    result += vertex

    for (successor in graph[vertex].orEmpty()) {
      inCount[successor] = inCount[successor] - 1
      if (inCount[successor] == 0) {
        queue += successor
      }
    }
  }

  if (result.size != inCount.size) {
    error("Cycle in graph detected, topological sort not possible")
  }

  return result
}

fun <N> WeightedGraph<N, Unit>.shortestPaths(source: N): DefaultMap<N, WeightedGraph.D<Unit>> = shortestPaths(
  source = source,
  startDistanceContext = Unit,
  zeroDistanceContext = Unit,
  maxDistanceContext = Unit,
  cost = { _, _ -> BigInteger.ONE },
  alterContext = { _, _ -> }
)

fun <N> WeightedGraph<N, Unit>.shortestPath(source: N, destination: N): BigInteger =
  shortestPaths(source)[destination].value

class WeightedGraph<N, ECtx>(
  val adj: Map<N, Set<E<N, ECtx>>>,
) {
  data class E<N, E>(val to: N, val context: E)
  data class QN<N, DC>(val n: N, val distance: D<DC>)
  data class D<DC>(val value: BigInteger, val context: DC)

  val nodes: List<N> = adj.keys.toList()

  fun <DC> shortestPaths(
    source: N,
    startDistanceContext: DC,
    zeroDistanceContext: DC,
    maxDistanceContext: DC,
    cost: (from: QN<N, DC>, to: E<N, ECtx>) -> BigInteger,
    alterContext: (to: E<N, ECtx>, altDistance: BigInteger) -> DC
  ): DefaultMap<N, D<DC>> {

    val dist = DefaultMap<N, D<DC>>(D(BigInteger.ZERO, zeroDistanceContext))
    val queue = PriorityQueue<QN<N, DC>>(adj.keys.size, compareBy(selector = { it.distance.value }))

    adj.keys.forEach { v ->
      if (v != source) dist[v] = D(INFINITY, maxDistanceContext)
      queue.add(if (v != source) QN(v, dist[v]) else QN(source, D(BigInteger.ZERO, startDistanceContext)))
    }

    while (queue.isNotEmpty()) {
      val u = queue.poll()

      if (u.distance.context == null) break

      adj[u.n]?.forEach neigh@{ edge ->
        val alt = dist[u.n].value + cost(u, edge)

        if (alt >= dist[edge.to].value) return@neigh

        val altDist = D(alt, alterContext(edge, alt))
        dist[edge.to] = altDist
        queue.add(QN(edge.to, altDist))
      }
    }
    return dist
  }

  fun <DC> shortestPath(
    source: N,
    destination: N,
    startDistanceContext: DC,
    zeroDistanceContext: DC,
    maxDistanceContext: DC,
    cost: (from: QN<N, DC>, to: E<N, ECtx>) -> BigInteger,
    alterContext: (to: E<N, ECtx>, altDistance: BigInteger) -> DC,
  ): D<DC> = shortestPaths(
    source = source,
    startDistanceContext = startDistanceContext,
    zeroDistanceContext = zeroDistanceContext,
    maxDistanceContext = maxDistanceContext,
    cost = cost,
    alterContext = alterContext
  )[destination]

  override fun toString() = adj.toString()

  companion object {
    val INFINITY: BigInteger by lazy { BigInteger.fromLong(Long.MAX_VALUE) * BigInteger.fromLong(Long.MAX_VALUE) }
  }
}
