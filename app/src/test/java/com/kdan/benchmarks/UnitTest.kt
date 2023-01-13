package com.kdan.benchmarks

import com.kdan.benchmarks.repository.CollectionsRepository
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.CollectionsViewModel
import com.kdan.benchmarks.viewmodel.ItemData
import com.kdan.benchmarks.viewmodel.MapsViewModel
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.ArrayList

const val ONE_MILLION = 1_000_000

class UnitTest {

    private val collVM = CollectionsViewModel()
    private val collRep = CollectionsRepository()
    private val mapsVM = MapsViewModel()
    private val mapsRep = MapsRepository()

    @Test
    fun createArrayList() {
        collRep.collectionSize = ONE_MILLION
        assertEquals(ONE_MILLION, collRep.createArrayList().size)
    }

    @Test
    fun createLinkedList() {
        collRep.collectionSize = ONE_MILLION
        assertEquals(ONE_MILLION, collRep.createLinkedList().size)
    }

    @Test
    fun createCopyOnWriteArrayList() {
        collRep.collectionSize = ONE_MILLION / 10 // made dividing for getting a quick result
        assertEquals(ONE_MILLION / 10, collRep.createCopyOnWriteArrayList().size)
    }

    @Test
    fun createTreeMap() {
        mapsRep.collectionSize = ONE_MILLION
        assertEquals(ONE_MILLION, mapsRep.createTreeMap().size)
    }

    @Test
    fun createHashMap() {
        mapsRep.collectionSize = ONE_MILLION
        assertEquals(ONE_MILLION, mapsRep.createHashMap().size)
    }

    @Test
    fun addingArraySavingLoading() {  // adding in the ArrayList, saving result, loading result
        setupItems(0)
        setupNumbers(0)
        setupState(0)
        collRep.isRunning = true
        val array: ArrayList<Byte> = collRep.createArrayList()
        array.add(0, "100".toByte())
        if (array[0] == "100".toByte()) {
            collRep.positions += 0
            collRep.saveResult()
        }
        array.clear()
        collVM.loadResult()
        assertTrue(collVM.positions.contains(0))
    }

    @Test
    fun addingMapSavingLoading() { // adding in the TreeMap, saving result, loading result
        setupItems(1)
        setupNumbers(1)
        setupState(1)
        mapsRep.isRunning = true
        val map: TreeMap<Int, Byte> = mapsRep.createTreeMap()
        map[-1] = "100".toByte()
        if (map[-1] == "100".toByte()) {
            mapsRep.positions += 1
            mapsRep.saveResult()
        }
        map.clear()
        mapsVM.loadResult()
        assertTrue(mapsVM.positions.contains(1))
    }

    private fun setupItems(indexOfTab: Int) { // is needed because of lateinit in repositories
        when (indexOfTab) {
            0 -> {
                if (collVM.items.isNotEmpty()) return
                repeat(21) { collVM.items += ItemData(id = it, initialText = "initialText") }
                collRep.items = collVM.items
            }
            1 -> {
                if (mapsVM.items.isNotEmpty()) return
                repeat(6) { mapsVM.items += ItemData(id = it, initialText = "initialText") }
                mapsRep.items = mapsVM.items
            }
            else -> throw Exception("No such fragment")
        }
    }

    private fun setupNumbers(
        indexOfTab: Int,
        collectionSize: Int = ONE_MILLION,
        elementsAmount: Int = ONE_MILLION
    ) {
        when (indexOfTab) {
            0 -> {
                collVM.collectionSize = collectionSize
                collRep.collectionSize = collVM.collectionSize
                collVM.elementsAmount = elementsAmount
                collRep.elementsAmount = collVM.elementsAmount
            }
            1 -> {
                mapsVM.collectionSize = collectionSize
                mapsRep.collectionSize = mapsVM.collectionSize
                mapsVM.elementsAmount = elementsAmount
                mapsRep.elementsAmount = mapsVM.elementsAmount
            }
            else -> throw Exception("No such fragment")
        }
    }

    private fun setupState(indexOfTab: Int) {
        when (indexOfTab) {
            0 -> {
                collRep.isRunning = false
                collRep.isStopped = true
                collRep.positions.clear()
                collVM.positions.clear()
                Callback.Result.positionsCollections.clear()
            }
            1 -> {
                mapsRep.isRunning = false
                mapsRep.isStopped = true
                mapsRep.positions.clear()
                mapsVM.positions.clear()
                Callback.Result.positionsMaps.clear()
            }
            else -> throw Exception("No such fragment")
        }
    }
}