package com.kdan.benchmarks.viewmodel

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