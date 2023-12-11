package com.example.adventofcode2023

import org.junit.Test
import java.lang.StringBuilder
import kotlin.math.abs

class AdventOfCode11 {

    @Test
    fun `Advent of code day 11 first half`() {
        FileLoader.getFileFromPath(this, "inputDay11.txt")?.let { file ->
            val universe = file.readLines()
            val horizontalExpansionIndices = getHorizontalExpansionIndices(universe)
            val verticalExpansionIndices = getVerticalExpansionIndices(universe)
            val galaxies = readGalaxies(universe)
            val galaxiesDistances = mutableListOf<Long>()
            for (i in 0..<galaxies.lastIndex) {
                for (j in i + 1 .. galaxies.lastIndex) {
                    val path = calculateShortestPathWithExpansionIndices(
                        galaxies[i],
                        galaxies[j],
                        horizontalExpansionIndices,
                        verticalExpansionIndices,
                        2,
                    )
                    galaxiesDistances.add(path)
                }
            }
            val sum = galaxiesDistances.sum()
            println(sum)
        }
    }

    @Test
    fun `Advent of code day 11 second half`() {
        FileLoader.getFileFromPath(this, "inputDay11.txt")?.let { file ->
            val universe = file.readLines()
            val horizontalExpansionIndices = getHorizontalExpansionIndices(universe)
            val verticalExpansionIndices = getVerticalExpansionIndices(universe)
            val galaxies = readGalaxies(universe)
            val galaxiesDistances = mutableListOf<Long>()
            for (i in 0..<galaxies.lastIndex) {
                for (j in i + 1 .. galaxies.lastIndex) {
                    val path = calculateShortestPathWithExpansionIndices(
                        galaxies[i],
                        galaxies[j],
                        horizontalExpansionIndices,
                        verticalExpansionIndices,
                        1000000,
                        )
                    galaxiesDistances.add(path)
                }
            }
            val sum = galaxiesDistances.sum()
            println(sum)
        }
    }

    private fun expandTheUniverse(map: List<String>): List<String> {:s
        val horizontalExpansion = getHorizontalExpansionIndices(map)
        val verticalExpansion = getVerticalExpansionIndices(map)

        val horizontalExpanded = mutableListOf<String>()
        for (y in 0..map.lastIndex) {
            val newRow = StringBuilder()
            for (x in 0..map[y].lastIndex) {
                if (x in horizontalExpansion) {
                    newRow.append("..")
                } else {
                    newRow.append(map[y][x])
                }
            }
            horizontalExpanded.add(newRow.toString())
        }

        val verticalExpanded = mutableListOf<String>()
        for (y in 0..map.lastIndex) {
            if (y in verticalExpansion) {
                verticalExpanded.add(".".repeat(horizontalExpanded.first().length))
            }
            verticalExpanded.add(horizontalExpanded[y])
        }
        return verticalExpanded
    }

    private fun getHorizontalExpansionIndices(map: List<String>): List<Int> {
        val horizontalExpansion = mutableListOf<Int>()
        for (i in 0..map.first().lastIndex) {
            val allFromColumn = map.map { it[i] }
            if (allFromColumn.count { it == '#' } == 0 ) {
                horizontalExpansion.add(i)
            }
        }
        return horizontalExpansion
    }

    private fun getVerticalExpansionIndices(map: List<String>): List<Int> {
        val verticalExpansion = mutableListOf<Int>()
        for (i in 0..map.lastIndex) {
            val allFromRow = map[i]
            if (allFromRow.count { it == '#' } == 0 ) {
                verticalExpansion.add(i)
            }
        }
        return verticalExpansion
    }

    private fun readGalaxies(map: List<String>): List<Pair<Int, Int>> {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '#') {
                    galaxies.add(Pair(x, y))
                }
            }
        }
        return galaxies
    }

    private fun calculateShortestPathWithExpansionIndices(
        pair1: Pair<Int, Int>,
        pair2: Pair<Int, Int>,
        horizontalExpansion: List<Int>,
        verticalExpansion: List<Int>,
        multiplier: Int,
    ): Long {
        var amount = 0L
        val x1 = pair1.first.coerceAtMost(pair2.first)
        val x2 = pair1.first.coerceAtLeast(pair2.first)
        for (x in x1 + 1..x2) {
            amount += if (x in horizontalExpansion) {
                multiplier
            } else {
                1
            }
        }
        val y1 = pair1.second.coerceAtMost(pair2.second)
        val y2 = pair1.second.coerceAtLeast(pair2.second)
        for (y in y1 + 1..y2) {
            amount += if (y in verticalExpansion) {
                multiplier
            } else {
                1
            }
        }
        return amount
    }
}
