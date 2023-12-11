package com.example.adventofcode2023

import java.io.File


class FileLoader {
    companion object {
        fun getFileFromPath(obj: Any, fileName: String): File? {
            obj.javaClass.classLoader?.getResource(fileName)?.let {
                return File(it.path)
            }
            return null
        }
    }
}