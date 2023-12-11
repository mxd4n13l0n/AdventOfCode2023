package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay6 {

    @Test
    fun `Advent of code day 6 first half`() {
        FileLoader.getFileFromPath(this, "inputDay6.txt")?.let { file ->
            val fileLines = file.readLines()
            val times = fileLines
                .first()
                .removePrefix("Time:")
                .split(' ')
                .filterNot { it == "" }
                .map { it.toLong() }
            val recordDistance = fileLines
                .last()
                .removePrefix("Distance:")
                .split(' ')
                .filterNot { it == "" }
                .map { it.toLong() }
            println(times)
            println(recordDistance)
            var possibilities = 1L
            for (i in times.indices) {
                val currentTime = times[i]
                val distanceRecord = recordDistance[i]
                val possibilitiesToBeatGame = calculatePossibilities(currentTime, distanceRecord)
                possibilities *= possibilitiesToBeatGame
            }
            println(possibilities)
        }
    }

    private fun calculatePossibilities(time: Long, distanceRecord: Long): Long {
        var possibilities = 0L
        for (timeSpentOnButton in 0..time) {
            val distanceTravelled = timeSpentOnButton * (time - timeSpentOnButton)
            if (distanceTravelled > distanceRecord) {
                possibilities += 1
            }
        }
        return possibilities
    }

    @Test
    fun `Advent of code day 6 second half`() {
        FileLoader.getFileFromPath(this, "inputDay6.txt")?.let { file ->
            val fileLines = file.readLines()
            val time = fileLines
                .first()
                .removePrefix("Time:")
                .filter { it.isDigit() }
                .toLong()
            val recordDistance = fileLines
                .last()
                .removePrefix("Distance:")
                .filter { it.isDigit() }
                .toLong()
            println(time)
            println(recordDistance)
            val possibilitiesToBeatGame = calculatePossibilities(time, recordDistance)
            println(possibilitiesToBeatGame)
        }
    }
}