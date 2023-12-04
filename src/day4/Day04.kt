package day4

import println
import readInput
import kotlin.math.pow

data class Card(val id: Int,var count: Int,var matches: Int)

fun countMatches(line: String): Int {
    val (winningNumbersString,myNumberString) = line.substringAfter(": ").split(" | ")
    val regex = Regex("\\d+")
    val winningNumbers = regex.findAll(winningNumbersString).map { it.value.toInt() }
    return regex.findAll(myNumberString).map { it.value.toInt() }.count {
        it in winningNumbers
    }
}
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val count = countMatches(line)
            if(count > 0) 2.0.pow(count - 1).toInt() else 0
        }
    }

    fun part2(input: List<String>): Int {
        val cards = input.mapIndexed { index, line ->
            val matches = countMatches(line)
            Card(index + 1,1,matches)
        }

        for ((i,card) in cards.withIndex()){
            for(j in (i+1)..(i + card.matches)) {
                cards[j].count += card.count
            }
        }
        return cards.sumOf { it.count }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/part1test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("day4/part1")
    part1(input).println()
    part2(input).println()
}
