package com.kdan.benchmarks

import com.kdan.benchmarks.repository.CollectionsRepository
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.data.Callback
import com.kdan.benchmarks.data.ItemData
import com.kdan.benchmarks.ui.collections.CollsPresenter
import com.kdan.benchmarks.ui.maps.MapsPresenter
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.ArrayList

const val ONE_MILLION = 1_000_000

class UnitTest {

    private val collPres = CollsPresenter()
    private val collRep = CollectionsRepository()
    private val mapsPres = MapsPresenter()
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
        collPres.loadResult()
        assertTrue(collPres.positions.contains(0))
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
        mapsPres.loadResult()
        assertTrue(mapsPres.positions.contains(1))
    }

    private fun setupItems(indexOfTab: Int) { // is needed because of lateinit in repositories
        when (indexOfTab) {
            0 -> {
                if (collPres.items.isNotEmpty()) return
                repeat(21) { collPres.items += ItemData(id = it, initialText = "initialText") }
                collRep.items = collPres.items
            }
            1 -> {
                if (mapsPres.items.isNotEmpty()) return
                repeat(6) { mapsPres.items += ItemData(id = it, initialText = "initialText") }
                mapsRep.items = mapsPres.items
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
                collPres.collectionSize = collectionSize
                collRep.collectionSize = collPres.collectionSize
                collPres.elementsAmount = elementsAmount
                collRep.elementsAmount = collPres.elementsAmount
            }
            1 -> {
                mapsPres.collectionSize = collectionSize
                mapsRep.collectionSize = mapsPres.collectionSize
                mapsPres.elementsAmount = elementsAmount
                mapsRep.elementsAmount = mapsPres.elementsAmount
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
                collPres.positions.clear()
                Callback.Result.positionsCollections.clear()
            }
            1 -> {
                mapsRep.isRunning = false
                mapsRep.isStopped = true
                mapsRep.positions.clear()
                mapsPres.positions.clear()
                Callback.Result.positionsMaps.clear()
            }
            else -> throw Exception("No such fragment")
        }
    }
}