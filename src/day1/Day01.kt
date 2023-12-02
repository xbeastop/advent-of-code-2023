package day1

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            "${it.first(Char::isDigit)}${it.last(Char::isDigit)}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val replacedInput = input.map {
            it.replace("one","o1e")
                .replace("two","t2o")
                .replace("three","t3ree")
                .replace("four","f4our")
                .replace("five","f5ve")
                .replace("six","s6x")
                .replace("seven","s7ven")
                .replace("eight","e8ght")
                .replace("nine","n9ne")
                .replace("zero","z0ro")
        }
        return part1(replacedInput)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1/part1test")
    val testInputPart2 = readInput("day1/part2test")
    check(part1(testInput) == 142)
    check(part2(testInputPart2) == 281)

    val input = readInput("day1/part1")
    val input2 = readInput("day1/part2")
    part1(input).println()
    part2(input2).println()
}
