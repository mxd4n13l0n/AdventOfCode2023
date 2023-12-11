package com.example.adventofcode2023

import org.junit.Test

class AdventOfCode10 {

    @Test
    fun `Advent of code day 10 first half`() {
        FileLoader.getFileFromPath(this, "inputDay10.txt")?.let { file ->
            val map = file.readLines()
            val startingPosition = findStartingPosition(map)
            println("evaluating to north:")
            val resultToNorth = evaluatePath(map, startingPosition, Directions.North).size
            println("result: $resultToNorth")
            if (resultToNorth > 0) {
                println("The answer is ${resultToNorth/2}")
                return
            }

            println("evaluating to south:")
            val resultToSouth = evaluatePath(map, startingPosition, Directions.South).size
            println("result: $resultToSouth")
            if (resultToSouth > 0) {
                println("The answer is ${resultToSouth/2}")
                return
            }

            println("evaluating to west:")
            val resultToWest = evaluatePath(map, startingPosition, Directions.West).size
            println("result: $resultToWest")
            if (resultToWest > 0) {
                println("The answer is ${resultToWest/2}")
                return
            }

            println("evaluating to east:")
            val resultToEast = evaluatePath(map, startingPosition, Directions.East).size
            println("result: $resultToEast")
            if (resultToEast > 0) {
                println("The answer is ${resultToEast/2}")
                return
            }
        }
    }

    @Test
    fun `Advent of code day 10 second half`() {
        FileLoader.getFileFromPath(this, "inputDay10.txt")?.let { file ->
            val map = file.readLines()
            val startingPosition = findStartingPosition(map)
            val pipePoints = evaluatePath(map, startingPosition, Directions.North)
            val pointsInside = getPointsInside(map, pipePoints)
            println("pipe points: ${pipePoints.size}")
            println("points inside: $pointsInside")
        }
    }

    private fun findStartingPosition(map: List<String>): Pair<Int, Int> {
        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == 'S') {
                    return Pair(x, y)
                }
            }
        }
        println("starting position of the animal not found")
        return Pair(0, 0)
    }

    private fun readPosition(map: List<String>, position: Pair<Int, Int>): Char {
        return map[position.second][position.first]
    }

    private fun evaluatePath(map: List<String>, startingPosition: Pair<Int, Int>, direction: Directions): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()
        var currentPosition = startingPosition.copy()
        var currentDirection = direction
        while(true) {
            when(currentDirection) {
                Directions.North -> {
                    if (currentPosition.second == 0) {
                        return emptyList()
                    }
                    currentPosition = currentPosition.copy(second = currentPosition.second - 1)
                    when(readPosition(map, currentPosition)) {
                        'S' -> {
                            points.add(currentPosition)
                            return points
                        }
                        '|' -> {
                            points.add(currentPosition)
                        }
                        '7' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.West
                        }
                        'F' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.East
                        }
                        else -> return emptyList()
                    }
                }
                Directions.South -> {
                    if (currentPosition.second == map.lastIndex) {
                        return emptyList()
                    }
                    currentPosition = currentPosition.copy(second = currentPosition.second + 1)
                    when(readPosition(map, currentPosition)) {
                        'S' -> {
                            points.add(currentPosition)
                            return points
                        }
                        '|' -> {
                            points.add(currentPosition)
                        }
                        'L' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.East
                        }
                        'J' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.West
                        }
                        else -> return emptyList()
                    }
                }
                Directions.West -> {
                    if (currentPosition.first == 0) {
                        return emptyList()
                    }
                    currentPosition = currentPosition.copy(first = currentPosition.first - 1)
                    when(readPosition(map, currentPosition)) {
                        'S' -> {
                            points.add(currentPosition)
                            return points
                        }
                        '-' -> {
                            points.add(currentPosition)
                        }
                        'L' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.North
                        }
                        'F' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.South
                        }
                        else -> return emptyList()
                    }
                }
                Directions.East -> {
                    if (currentPosition.first == map.first().lastIndex) {
                        return emptyList()
                    }
                    currentPosition = currentPosition.copy(first = currentPosition.first + 1)
                    when(readPosition(map, currentPosition)) {
                        'S' -> {
                            points.add(currentPosition)
                            return points
                        }
                        '-' -> {
                            points.add(currentPosition)
                        }
                        'J' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.North
                        }
                        '7' -> {
                            points.add(currentPosition)
                            currentDirection = Directions.South
                        }
                        else -> return emptyList()
                    }
                }
            }
        }
    }

    private fun getPointsInside(map: List<String>, polygon: List<Pair<Int, Int>>): Int {
        /**
         * valid pipes are the ones that connects through north to either south, east or west
         */
        val validPipes = "S|LJ"
        var points = 0
        for(y in map.indices) {
            var isInside = false
            val line = map[y]
            for(x in line.indices) {
                val char = line[x]
                val currentPosition = Pair(x,y)
                if (currentPosition in polygon && char in validPipes) {
                    isInside = isInside.not()
                }
                if (currentPosition !in polygon && isInside) {
                    points += 1
                }
            }
        }
        return points
    }

    enum class Directions {
        North, South, West, East
    }
}
