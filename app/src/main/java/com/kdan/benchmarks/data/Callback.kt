package com.kdan.benchmarks.data

interface Callback {

    interface SavingResult {
        fun saveResult()
    }

    interface LoadingResult {
        fun loadResult()
    }

    object Result {
        val positionsMaps = mutableSetOf<Int>()
        val positionsCollections = mutableSetOf<Int>()
    }
}