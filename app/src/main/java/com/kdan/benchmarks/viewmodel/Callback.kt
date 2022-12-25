package com.kdan.benchmarks.viewmodel

interface Callback {
    fun loadResult()
    fun saveResult()

    object Result {
        var items = mutableListOf<ItemData>()
        var temp = mutableSetOf<Int>()
    }
}