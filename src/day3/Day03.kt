package day3

import println
import readInput

fun checkSurroundings(
    charIndex: Int,
    currentLine: String,
    previousLine: String,
    nextLine: String
): Boolean {
    //check currentLine
    val leftIndex = (charIndex - 1).coerceAtLeast(0)
    val rightIndex = (charIndex + 1).coerceAtMost(currentLine.lastIndex)

    //top and bottom is same
    fun String.checkAnyoneIsNotPeriodOrDigit(vararg indices: Int): Boolean {
        return indices.any {
            this[it].isDigit().not() && this[it] != '.'
        }
    }
    if (currentLine.checkAnyoneIsNotPeriodOrDigit(leftIndex, rightIndex)) {
        return true
    }
    if (previousLine != currentLine) {
        if (previousLine.checkAnyoneIsNotPeriodOrDigit(leftIndex, charIndex, rightIndex)) {
            return true
        }
    }
    if (nextLine != currentLine) {
        if (nextLine.checkAnyoneIsNotPeriodOrDigit(leftIndex, charIndex, rightIndex)) {
            return true
        }
    }

    return false
}

fun getGearRatio(
    charIndex: Int,
    col: Int,
    currentLine: String,
    previousLine: String,
    nextLine: String
): Int {

    data class Number(val value: Int, val col: Int)
    val leftIndex = (charIndex - 1).coerceAtLeast(0)
    val rightIndex = (charIndex + 1).coerceAtMost(currentLine.lastIndex)

    fun String.getNumberFromIndex(index: Int): Int? {
        val after = substring(index).takeWhile { it.isDigit() }
        val before = substring(0, index).reversed().takeWhile { it.isDigit() }.reversed()
        return "$before$after".toIntOrNull()
    }

    fun String.getSurroundingNumbers(col: Int, vararg indices: Int): List<Number> {
        return indices.map {
            if (this[it].isDigit()) {
                this.getNumberFromIndex(it)
            } else null
        }.mapNotNull { n ->
            n?.let { Number(it, col) }
        }
    }

    val finalList = mutableSetOf<Number>()
    finalList.addAll(
        currentLine.getSurroundingNumbers(col = col, leftIndex, rightIndex)
    )
    if (previousLine != currentLine) {
        finalList.addAll(
            previousLine.getSurroundingNumbers(col = col - 1, leftIndex, charIndex, rightIndex)
        )
    }
    if (nextLine != currentLine) {
        finalList.addAll(
            nextLine.getSurroundingNumbers(col = col + 1, leftIndex, charIndex, rightIndex)
        )
    }

    return if (finalList.size == 2)
        (finalList.first().value * finalList.last().value)
    else 0

}


fun checkNumberSurrounding(indexCharMap: Map<Int, Char>, inputLine: List<String>, index: Int): Boolean {
    return indexCharMap.any { (idx, _) ->
        checkSurroundings(
            charIndex = idx,
            currentLine = inputLine[index],
            previousLine = inputLine[(index - 1).coerceAtLeast(0)],
            nextLine = inputLine[(index + 1).coerceAtMost(inputLine.lastIndex)]
        )
    }
}


fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (i in input.indices) { // each line
            val indexCharMap = mutableMapOf<Int, Char>()
            for ((j, c) in input[i].withIndex()) {
                if (c.isDigit()) {
                    indexCharMap[j] = c
                } else if (c == '.') { // end of number check surroundings
                    if (checkNumberSurrounding(indexCharMap, input, i))
                        sum += indexCharMap.values.joinToString("").toInt()
                    indexCharMap.clear()
                } else {
                    if (indexCharMap.isNotEmpty()) {
                        sum += indexCharMap.values.joinToString("").toInt()
                    }
                    indexCharMap.clear()
                }
                if (j == input[i].lastIndex) { // end of line
                    if (checkNumberSurrounding(indexCharMap, input, i))
                        sum += indexCharMap.values.joinToString("").toInt()
                    indexCharMap.clear()
                }
            }
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var sum = 0
        for (i in input.indices) { // each line
            for ((j, c) in input[i].withIndex()) {
                if (c == '*') {
                    sum+=getGearRatio(
                        j, i, input[i], previousLine = input[(i - 1).coerceAtLeast(0)],
                        nextLine = input[(i + 1).coerceAtMost(input.lastIndex)]
                    )
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day3/part1test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("day3/part1")
    val input2 = readInput("day3/part2")
    part1(input).println()
    part2(input2).println()
}
