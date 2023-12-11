package com.example.adventofcode2023

import org.junit.Test

class AdventOfCodeDay3 {

    @Test
    fun `Advent of code day 3 first half`() {
        FileLoader.getFileFromPath(this, "inputDay3.txt")?.let { file ->
            var sum = 0L
            val fileLines = file.readLines()
            for(i in fileLines.indices) {
                val currentLine = fileLines[i]
                val previousLine = fileLines.getOrNull(i - 1)
                val nextLine = fileLines.getOrNull(i + 1)

                var indexForFirstNumberRemembered: Int? = null
                var indexForLastNumberRemembered: Int? = null
                for (characterIndex in currentLine.indices) {
                    if (currentLine[characterIndex].isDigit()) {
                        if (indexForFirstNumberRemembered == null) {
                            indexForFirstNumberRemembered = characterIndex
                            indexForLastNumberRemembered = characterIndex
                        } else {
                            indexForLastNumberRemembered = characterIndex
                        }
                    }
                    if (currentLine[characterIndex].isDigit().not() || characterIndex == currentLine.lastIndex) {
                        if (indexForFirstNumberRemembered != null && indexForLastNumberRemembered != null) {
                            if (hasAtLeastOneAdjacentSymbol(currentLine, previousLine, nextLine, indexForFirstNumberRemembered, indexForLastNumberRemembered)) {
                                val subChar = currentLine.substring(indexForFirstNumberRemembered, indexForLastNumberRemembered + 1)
                                sum += subChar.toLong()
                            }
                        }
                        indexForFirstNumberRemembered = null
                        indexForLastNumberRemembered = null
                    }
                }
            }
            println(sum)
        }
    }


    private fun hasAtLeastOneAdjacentSymbol(currentLine: String, previousLine: String?, nextLine: String?, startIndex: Int, endIndex: Int): Boolean {
        currentLine.getOrNull(startIndex - 1)?.let { adjacentLeft ->
            if (isSymbol(adjacentLeft)) {
                return true
            }
        }

        currentLine.getOrNull(endIndex + 1)?. let { adjacentRight ->
            if (isSymbol(adjacentRight)) {
                return true
            }
        }

        previousLine?.let { upperLine ->
            val upperLineFirstIndex = if (upperLine.getOrNull(startIndex - 1) != null) {
                startIndex - 1
            } else {
                startIndex
            }
            val upperLineLastIndex = if (upperLine.getOrNull(endIndex + 1) != null) {
                endIndex + 1
            } else {
                endIndex
            }
            val subStringForUpperLine = upperLine.substring(upperLineFirstIndex, upperLineLastIndex + 1)
            subStringForUpperLine.forEach { upperLineCharacter ->
                if (isSymbol(upperLineCharacter)) {
                    return true
                }
            }
        }

        nextLine?.let { lowerLine ->
            val lowerLineFirstIndex = if (lowerLine.getOrNull(startIndex - 1) != null) {
                startIndex - 1
            } else {
                startIndex
            }
            val lowerLineLastIndex = if (lowerLine.getOrNull(endIndex + 1) != null) {
                endIndex + 1
            } else {
                endIndex
            }
            val subStringForLowerLine = lowerLine.substring(lowerLineFirstIndex, lowerLineLastIndex + 1)
            subStringForLowerLine.forEach { lowerLineCharacter ->
                if (isSymbol(lowerLineCharacter)) {
                    return true
                }
            }
        }

        return false
    }

    /**
     * A symbol means that is not any digit or any dot (.) character
     */
    private fun isSymbol(character: Char): Boolean {
        return character.isDigit().not() && character != '.'
    }

    @Test
    fun `Advent of code day 3 second half`() {
        FileLoader.getFileFromPath(this, "inputDay3.txt")?.let { file ->
            var sum = 0L
            val fileLines = file.readLines()
            for(i in fileLines.indices) {
                val currentLine = fileLines[i]
                val previousLine = fileLines.getOrNull(i - 1)
                val nextLine = fileLines.getOrNull(i + 1)
                findGearsIndices(currentLine).forEach { gearIndex ->
                    val adjacentNumbers = mutableListOf<Int>()

                    // seek numbers to left of the gear
                    currentLine.getOrNull(gearIndex - 1)?.let { _ ->
                        findNumberFromPosition(gearIndex - 1, currentLine)?.let { numberFound ->
                            adjacentNumbers.add(numberFound)
                        }
                    }

                    // seek numbers to right of the gear
                    currentLine.getOrNull(gearIndex + 1)?.let { _ ->
                        findNumberFromPosition(gearIndex + 1, currentLine)?.let { numberFound ->
                            adjacentNumbers.add(numberFound)
                        }
                    }

                    // seek numbers to top of the gear
                    previousLine?.let { topLine ->
                        adjacentNumbers.addAll(findNumbersAdjacentToGear(gearIndex, topLine))
                    }

                    // seek numbers to bottom of the gear
                    nextLine?.let { bottomLine ->
                        adjacentNumbers.addAll(findNumbersAdjacentToGear(gearIndex, bottomLine))
                    }
                    if (adjacentNumbers.size == 2) {
                        val gearRatio: Long = adjacentNumbers.first().toLong() * adjacentNumbers.last()
                        sum += gearRatio
                    }
                }
            }
            println(sum)
        }
    }

    private fun findGearsIndices(line: String): List<Int> {
        val gearIndices = mutableListOf<Int>()
        for (i in line.indices) {
            if (line[i] == '*') {
                gearIndices.add(i)
            }
        }
        return gearIndices
    }

    private fun findNumbersAdjacentToGear(gearIndex: Int, lineForSeek: String): List<Int> {
        val result = mutableListOf<Int>()
        if (lineForSeek[gearIndex].isDigit()) {
            findNumberFromPosition(gearIndex, lineForSeek)?.let { number ->
                result.add(number)
            }
        } else {
            findNumberFromPosition(gearIndex - 1, lineForSeek)?.let { number ->
                result.add(number)
            }
            findNumberFromPosition(gearIndex + 1, lineForSeek)?.let { number ->
                result.add(number)
            }
        }
        return result
    }

    private fun findNumberFromPosition(index: Int, lineForSeek: String): Int? {
        lineForSeek.getOrNull(index)?.let {
            if (it.isDigit().not()) {
                return null
            }

            var startIndex = index
            var endIndex = index
            while (lineForSeek.getOrNull(startIndex - 1) != null && lineForSeek[startIndex - 1].isDigit()) {
                startIndex -= 1
            }
            while (lineForSeek.getOrNull(endIndex + 1) != null && lineForSeek[endIndex + 1].isDigit()) {
                endIndex += 1
            }
            return lineForSeek.substring(startIndex, endIndex + 1).toInt()
        }
        return null
    }
}