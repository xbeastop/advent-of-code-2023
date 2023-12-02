package day2

import println
import readInput

data class Color(val red: Int, val green: Int, val blue: Int) {
    operator fun plus(other: Color) = Color(
        red + other.red, green + other.green, blue + other.blue
    )
}

fun String.getGameId() = substringBefore(":").substringAfter(" ").toInt()
fun String.toGames() = substringAfter(":").split(";")
fun String.numOfColor(color: String) = takeIf { it.contains(color) }?.trim()?.substringBefore(" ")?.toInt() ?: 0
fun String.toListOfColors() = split(",").map { colors ->
    Color(
        red = colors.numOfColor("red"),
        green = colors.numOfColor("green"),
        blue = colors.numOfColor("blue"),
    )
}.reduce(Color::plus)

fun main() {
    fun part1(input: List<String>, test: Color): Int {
        return input.sumOf { line ->
            val colors = line.toGames().map { game -> game.toListOfColors() }

            if (colors.all { color ->
                    color.red <= test.red && color.green <= test.green && color.blue <= test.blue
                }) line.getGameId() else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val colors = line.toGames().map { game ->
                game.toListOfColors()
            }
            val maxRed = colors.maxOf { it.red }
            val maxGreen = colors.maxOf { it.green }
            val maxBlue = colors.maxOf { it.blue }
            maxRed * maxGreen * maxBlue
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/part1test")
    val testInputPart2 = readInput("day2/part2test")
    val test1condition = Color(12, 13, 14)
    check(part1(testInput, test1condition) == 8)
    check(part2(testInputPart2) == 2286)

    val input = readInput("day2/part1")
    val input2 = readInput("day2/part2")
    part1(input, test1condition).println()
    part2(input2).println()
}
