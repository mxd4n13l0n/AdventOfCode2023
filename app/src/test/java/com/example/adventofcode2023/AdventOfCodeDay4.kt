package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay4 {

    @Test
    fun `Advent of code day 4 first half`() {
        FileLoader.getFileFromPath(this, "inputDay4.txt")?.let { file ->
            var sum = 0L
            val fileLines = file.readLines()
            fileLines.forEach { line ->
                var pointsForCard = 0
                val numbers = extractNumbers(line)
                numbers.first().forEach { winningNumber ->
                    if (numbers.last().contains(winningNumber)) {
                        pointsForCard = if (pointsForCard == 0) 1 else pointsForCard + pointsForCard
                    }
                }
                sum += pointsForCard
            }
            println(sum)
        }
    }

    @Test
    fun `Advent of code day 4 second half`() {
        FileLoader.getFileFromPath(this, "inputDay4.txt")?.let { file ->
            val scratchCardCount = mutableMapOf<Int, Long>()
            val fileLines = file.readLines()
            for(i in fileLines.indices) {
                scratchCardCount[i] = 1
            }

            for(i in fileLines.indices) {
                val currentCardAmount = scratchCardCount.getOrDefault(i, 0)
                if (currentCardAmount > 0) {
                    val currentLine = fileLines[i]
                    val numbers = extractNumbers(currentLine)
                    val wins = calculateWins(numbers.first(), numbers.last())
                    // Add cards multiplied for the current amount
                    if (i < fileLines.lastIndex) {
                        for (j in i+1 .. Math.min(fileLines.lastIndex, i + wins)) {
                            scratchCardCount[j] = scratchCardCount.getOrDefault(j, 0) + currentCardAmount
                        }
                    }
                }
            }
            val sum = scratchCardCount.values.sum()
            println(sum)
        }
    }

    private fun extractNumbers(line: String): List<Set<String>> {
        val allNumbers = line.split(':').last().split('|')
        val winningNumbers = allNumbers.first().trim().split(' ').filterNot { it == "" }.toSet()
        val myNumbers = allNumbers.last().trim().split(' ').filterNot { it == "" }.toSet()
        return listOf(winningNumbers, myNumbers)
    }

    private fun calculateWins(winningNumbers: Set<String>, numbers: Set<String>): Int {
        var pointsForCard = 0
        winningNumbers.forEach { winningNumber ->
            if (numbers.contains(winningNumber)) {
                pointsForCard += 1
            }
        }
        return pointsForCard
    }
}