const val NOT_FOUND = -1

private val START_SEARCH = Pair(NOT_FOUND, NOT_FOUND)

fun Pair<Int, Int>.fixFindings() = if (second == NOT_FOUND) Pair(first, first) else this

val numberWords = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

val minimumWordLength = numberWords.keys.map { it.length }.min()

fun main() {

    fun parseForNumberOrWord(line: String): Int? {
        val number = if (line.isNotEmpty()) {
            line.take(1).toIntOrNull() ?: run {
                val foundWord = numberWords.keys.firstOrNull { line.startsWith(it) }
                foundWord?.let {
                    numberWords.get(it)
                }
            }
        } else null
        return number
    }

    tailrec fun parseLineForBookendNumbersOrWords(
        line: String,
        findings: Pair<Int, Int> = START_SEARCH
    ): Pair<Int, Int> {
        val maybeNumber = parseForNumberOrWord(line)
        val updatedFindings = maybeNumber?.let {
            if (findings.first == NOT_FOUND)
                Pair(it, findings.second)
            else
                Pair(findings.first, it)
        } ?: findings
        return if (line.length <= 1) updatedFindings.fixFindings()
        else parseLineForBookendNumbersOrWords(line.drop(1), updatedFindings)
    }

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
        return if (line.length <= 1) updatedFindings.fixFindings() else parseLineForBookendNumbers(
            line.drop(1),
            updatedFindings
        )
    }

    fun findBookendNumbers(line: String): Pair<Int, Int> {
        val initial = line.first { it.isDigit() }
        val final = line.last { it.isDigit() }
        return Pair(initial.digitToInt(), final.digitToInt())
    }

    fun part1(input: List<String>): Int {
        return input.map {
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
        return input.map {
            parseLineForBookendNumbersOrWords(it)
        }.map { (first, second) ->
            "$first$second"
        }.mapNotNull {
            it.toIntOrNull()
        }.sumOf {
            it
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    check(part1(input) == 53921)

    val testInput2 = readInput("Day01_test2")
    println("${part2(testInput2)}")
    check(part2(testInput2) == 281)

    part2(input).println()
}
