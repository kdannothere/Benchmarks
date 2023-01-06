package com.kdan.benchmarks

import com.kdan.benchmarks.repository.CollectionsRepository
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.ItemData
import org.junit.Test

import org.junit.Assert.*

const val ONE_MILLION = 1_000_000
const val TEN_MILLIONS = 10_000_000

class UnitTest: Callback.LoadingResult {

    private val collectionsRepository = CollectionsRepository()
    private val mapsRepository = MapsRepository()
    private val items = mutableListOf<ItemData>()
    private var collectionSize = 0
    private var elementsAmount = 0
    private val collPositions = mutableSetOf<Int>()
    private val mapsPositions = mutableSetOf<Int>()

    @Test
    fun test1() {
        setupItems()
        setupNumbers(collectionSize = ONE_MILLION, elementsAmount = ONE_MILLION)
        prepCollRep()
        collectionsRepository.startAll()
        loadResult()
        assertEquals(21, collPositions.size)
    }

    //@Test
    fun test2() {
        setupItems()
        setupNumbers(collectionSize = TEN_MILLIONS, elementsAmount = TEN_MILLIONS)
        prepCollRep()
        assertEquals(0, 0)
    }

    private fun prepCollRep() {
        collPositions.clear()
        collectionsRepository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
            it.isRunning = false
            it.isStopped = true
        }
    }

    private fun prepMapsRep() {
        mapsPositions.clear()
        mapsRepository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
            it.isRunning = false
            it.isStopped = true
        }
    }

    private fun setupItems() {
        items.clear()
        repeat(21) { items += ItemData(id = it, initialText = "initialText") }
    }

    private fun setupNumbers(collectionSize: Int, elementsAmount: Int) {
        this.collectionSize = collectionSize
        this.elementsAmount = elementsAmount
    }

    override fun loadResult() {
        collPositions.addAll(Callback.Result.positionsCollections)
        mapsPositions.addAll(Callback.Result.positionsMaps)
    }
}