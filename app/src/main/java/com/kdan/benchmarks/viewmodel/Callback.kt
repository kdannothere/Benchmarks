package com.kdan.benchmarks.viewmodel

interface Callback {

    interface SavingResult {
        fun saveResult()
    }

    interface LoadingResult {
        fun loadResult()
    }

    object Result {
        val temp = mutableSetOf<Int>()
    }
}