package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay9 {

    @Test
    fun `Advent of code day 9 part 1`() {
        FileLoader.getFileFromPath(this, "inputDay9.txt")?.let { file ->
            val fileLines = file.readLines()
            var sum = 0L
            fileLines.forEach { line  ->
                sum += extrapolateAndCalculateNext(line.split(' ').map { it.toInt()})
            }
            println(sum)
        }
    }

    @Test
    fun `Advent of code day 9 part 2`() {
        FileLoader.getFileFromPath(this, "inputDay9.txt")?.let { file ->
            val fileLines = file.readLines()
            var sum = 0L
            fileLines.forEach { line  ->
                sum += extrapolateAndCalculatePrevious(line.split(' ').map { it.toInt()})
            }
            println(sum)
        }
    }

    private fun extrapolateAndCalculateNext(numbers: List<Int>): Int {
        var currentList = numbers
        val matrix = mutableListOf<List<Int>>()
        matrix.add(currentList)
        while (currentList.all { it == 0 }.not()) {
            currentList = calculateDifferences(currentList)
            matrix.add(currentList)
        }
        var newNumber = 0
        for (i in matrix.lastIndex -1 downTo 0)  {
            newNumber += matrix[i].last()
        }
        return newNumber
    }

    private fun extrapolateAndCalculatePrevious(numbers: List<Int>): Int {
        var currentList = numbers
        val matrix = mutableListOf<List<Int>>()
        matrix.add(currentList)
        while (currentList.all { it == 0 }.not()) {
            currentList = calculateDifferences(currentList)
            matrix.add(currentList)
        }
        var newNumber = 0
        for (i in matrix.lastIndex -1 downTo 0)  {
            newNumber = matrix[i].first() - newNumber
        }
        return newNumber
    }

    private fun calculateDifferences(number: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 0..<number.lastIndex) {
            result.add(number[i + 1] - number[i])
        }
        return result
    }

}