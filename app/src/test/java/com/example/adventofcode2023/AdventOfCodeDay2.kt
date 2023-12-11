package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay2 {

    @Test
    fun `Advent of code day 2 first half`() {
        FileLoader.getFileFromPath(this, "inputDay2.txt")?.let { file ->
            var sum = 0L
            file.readLines().forEach { line ->
                val gameLine = line.split(':')
                if (isGamePossible(gameLine.last())) {
                    val gameID = extractGameID(gameLine.first())
                    sum += gameID
                }
            }
            println(sum)
        }
    }

    @Test
    fun `Advent of code 2 second half`() {
        FileLoader.getFileFromPath(this, "inputDay2.txt")?.let { file ->
            var sum = 0L
            file.readLines().forEach { line ->
                val gameLine = line.split(':')
                val minimumCubes = calculateMinimumCubeFromGame(gameLine.last())
                var multiplier = 1L
                minimumCubes.values.forEach { multiplier *= it }
                sum += multiplier
            }
            println(sum)
        }
    }

    private fun extractGameID(line: String): Long {
        return line.removePrefix("Game ").toLong()
    }

    private fun isGamePossible(line: String): Boolean {
        val subGames = line.trim().split(';').map { it.trim() }
        subGames.forEach { subGame ->
            val colors = subGame.split(',').map { it.trim() }
            colors.forEach { color ->
                MAX_COLORS.keys.forEach { colorMatch ->
                    if (color.endsWith(colorMatch)) {
                        val colorCount = color.removeSuffix(colorMatch).toLong()
                        if (colorCount > MAX_COLORS.getOrDefault(colorMatch, 0) ) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun calculateMinimumCubeFromGame(line: String): Map<String, Int> {
        val subGames = line.trim().split(';').map { it.trim() }
        val minimumColorCounts = mutableMapOf(
            " red" to 0,
            " green" to 0,
            " blue" to 0,
        )
        subGames.forEach { subGame ->
            val colors = subGame.split(',').map { it.trim() }
            colors.forEach { color ->
                minimumColorCounts.keys.forEach { colorMatch ->
                    if (color.endsWith(colorMatch)) {
                        val colorCount = color.removeSuffix(colorMatch).toInt()
                        val actualMinCountForColor = minimumColorCounts.getOrDefault(colorMatch, 0)
                        if (colorCount > actualMinCountForColor) {
                            minimumColorCounts[colorMatch] = colorCount
                        }
                    }
                }
            }
        }
        return minimumColorCounts
    }

    companion object {

        val MAX_COLORS = mapOf(
            " red" to 12,
            " green" to 13,
            " blue" to 14,
            )
    }
}