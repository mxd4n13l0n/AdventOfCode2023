package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay5 {

    data class MapRanges(
        val sourceStart: Long,
        val destinationStart: Long,
        val length: Long,
    )

    @Test
    fun `Advent of code day 5 first half`() {
        FileLoader.getFileFromPath(this, "inputDay5.txt")?.let { file ->
            val fileLines = file.readLines()
            val listOfSeeds = fileLines[0]
                .removePrefix("seeds: ")
                .split(" ")
                .filterNot { it == "" }
                .map { it.toLong() }
            val allMaps = mutableListOf<MutableList<MapRanges>>()

            var mapLevel = -1
            for (i in 2..fileLines.lastIndex) {
                val currentLine = fileLines[i]
                if (currentLine.contains("map:")) {
                    mapLevel += 1
                    allMaps.add(mutableListOf())
                } else if (currentLine.isNotEmpty() && currentLine.first().isDigit()) {
                    val currentList = allMaps[mapLevel]
                    populateListWithCurrentLine(currentList, currentLine)
                }
            }
            var lowestLocation = Long.MAX_VALUE
            listOfSeeds.forEach { seed ->
                val location = mapSeedToLocation(seed, allMaps)
                println("seed: $seed, location: $location")
                if (location < lowestLocation) {
                    lowestLocation = location
                }
            }
            println(lowestLocation)
        }
    }

    @Test
    fun `Advent of code day 5 second half`() {
        FileLoader.getFileFromPath(this, "inputDay5.txt")?.let { file ->
            val fileLines = file.readLines()
            val seedLineParsed = fileLines[0]
                .removePrefix("seeds: ")
                .split(" ")
                .filterNot { it == "" }
                .map { it.toLong() }
            val allMaps = mutableListOf<MutableList<MapRanges>>()

            var mapLevel = -1
            for (i in 2..fileLines.lastIndex) {
                val currentLine = fileLines[i]
                if (currentLine.contains("map:")) {
                    mapLevel += 1
                    allMaps.add(mutableListOf())
                } else if (currentLine.isNotEmpty() && currentLine.first().isDigit()) {
                    val currentList = allMaps[mapLevel]
                    populateListWithCurrentLine(currentList, currentLine)
                }
            }
            var lowestLocation = Long.MAX_VALUE

            seedLineParsed.chunked(2).forEach { seedInfo ->
                val start = seedInfo.first()
                val length = seedInfo.last()
                for (seed in start..<start + length) {
                    val location = mapSeedToLocation(seed, allMaps)
                    //println("seed: $seed, location: $location")
                    if (location < lowestLocation) {
                        lowestLocation = location
                    }
                }
            }


            println(lowestLocation)
        }
    }

    private fun populateListWithCurrentLine(mapRangeList: MutableList<MapRanges>, line: String) {
        val parsedLine = line.split(' ').map { it.toLong() }
        val destinationStart = parsedLine.first()
        val sourceStart = parsedLine[1]
        val length = parsedLine.last()
        mapRangeList.add(MapRanges(sourceStart, destinationStart, length))
    }

    private fun mapSeedToLocation(seed: Long, allMaps: List<List<MapRanges>>): Long {
        var result = seed
        allMaps.forEach { currentSet ->
            for (i in currentSet.indices) {
                val mapRangeInfo = currentSet[i]
                if (result >= mapRangeInfo.sourceStart && result < mapRangeInfo.sourceStart + mapRangeInfo.length) {
                    val delta = mapRangeInfo.destinationStart - mapRangeInfo.sourceStart
                    result += delta
                    break
                }
            }
        }
        return result
    }
}