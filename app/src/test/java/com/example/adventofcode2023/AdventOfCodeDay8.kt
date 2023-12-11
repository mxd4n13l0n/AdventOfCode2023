package com.example.adventofcode2023

import org.junit.Test
import kotlin.math.max

class AdventOfCodeDay8 {

    @Test
    fun `Advent of code day 8 part 1`() {
        FileLoader.getFileFromPath(this, "inputDay8.txt")?.let { file ->
            val fileLines = file.readLines()
            val instructions = fileLines.first()
            var stepCount = 0
            var instructionIndex = 0
            var currentValue = "AAA"
            val navigationMap = mutableMapOf<String, List<String>>()
            fileLines.subList(2, fileLines.size).forEach { line ->
                with (extractNode(line)) {
                    navigationMap[this.first] = this.second
                }
            }
            println(navigationMap)
            while (currentValue != "ZZZ") {
                if (instructionIndex > instructions.lastIndex) {
                    instructionIndex = 0
                }
                val node = navigationMap[currentValue]!!
                currentValue = nextNavigation(node, instructions[instructionIndex])
                instructionIndex += 1
                stepCount += 1
            }
            println(stepCount)
        }
    }

    @Test
    fun `Advent of code day 8 part 2`() {
        FileLoader.getFileFromPath(this, "inputDay8.txt")?.let { file ->
            val fileLines = file.readLines()
            val instructions = fileLines.first()
            val navigationMap = mutableMapOf<String, List<String>>()
            fileLines.subList(2, fileLines.size).forEach { line ->
                with (extractNode(line)) {
                    navigationMap[this.first] = this.second
                }
            }
            val currentValues = navigationMap.keys.filter { it.last() == 'A' }.toMutableList()
            println("Ghosts found: ${currentValues.size}")
            val stepCounts = mutableListOf<Long>()
            for(i in 0..currentValues.lastIndex) {
                var instructionIndex = 0
                var stepCount = 0
                while (currentValues[i].last() != 'Z') {
                    if (instructionIndex > instructions.lastIndex) {
                        instructionIndex = 0
                    }
                    val node = navigationMap[currentValues[i]]!!
                    currentValues[i] = nextNavigation(node, instructions[instructionIndex])
                    instructionIndex += 1
                    stepCount += 1
                }
                stepCounts.add(stepCount.toLong())
            }
            println("Calculating lcd for all values: $stepCounts")
            println(findLCMOfListOfNumbers(stepCounts))
        }
    }

    private fun extractNode(line: String): Pair<String, List<String>> {
        val splittedInfo = line.split('=')
        val node = splittedInfo
            .first()
            .trim()
        val nodeForDirections = splittedInfo
            .last()
            .trim()
            .removeSurrounding("(", ")")
            .split(',')
            .map { it.trim() }
        return Pair(node, nodeForDirections)
    }

    private fun nextNavigation(mapNode: List<String>, direction: Char): String {
        return if (direction == 'L') {
            mapNode.first()
        } else {
            mapNode.last()
        }
    }

    private fun findLCM(a: Long, b: Long): Long {
        val larger = max(a, b)
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    private fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }
}
