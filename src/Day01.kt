const val NOT_FOUND = -1

private val START_SEARCH = Pair(NOT_FOUND, NOT_FOUND)

fun Pair<Int, Int>.fixFindings() = if (second == NOT_FOUND) Pair(first, first) else this

fun main() {

    tailrec fun parseLineForBookendNumbers(line: String, findings: Pair<Int, Int> = START_SEARCH): Pair<Int, Int> {
        val maybeNumber = if (line.isNotEmpty()) {
            line.take(1).toIntOrNull()
        } else null
        val updatedFindings = maybeNumber?.let {
            if (findings.first == NOT_FOUND)
                Pair(it, findings.second)
            else
                Pair(findings.first, it)
        } ?: findings
        return if (line.length == 1) updatedFindings.fixFindings() else parseLineForBookendNumbers(line.drop(1), updatedFindings)
    }

    fun findBookendNumbers(line: String): Pair<Int, Int> {
        val initial = line.first { it.isDigit() }
        val final = line.last { it.isDigit() }
        return Pair(initial.digitToInt(), final.digitToInt())
    }

    fun part1(input: List<String>): Int {
        return input.map {
//            parseLineForBookendNumbers(it)
            findBookendNumbers(it)
        }.map { (first, second) ->
            "$first$second"
        }.mapNotNull {
            it.toIntOrNull()
        }.sumOf {
            it
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    check(part1(input) == 53921)
    part2(input).println()
}
