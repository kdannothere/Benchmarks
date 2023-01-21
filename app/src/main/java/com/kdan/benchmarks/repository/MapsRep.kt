package com.kdan.benchmarks.repository

import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.ItemData
import java.util.TreeMap

class MapsRep(
    var collectionSize: Int = 0,
    var elementsAmount: Int = 0
) : Callback.SavingResult {

    var items = mutableListOf<ItemData>()
    var isRunning = false
    var isStopped = true
    private val positions = mutableSetOf<Int>()

    fun startAll() {
        isRunning = true
        isStopped = false
        changeAllBars()
        repeat(items.size) {
            if (!isRunning) {
                changeAllBars(true)
                isStopped = true
                return
            }
            when (it) {
                0 -> addingTreeMap(it)
                1 -> searchingTreeMap(it)
                2 -> removingTreeMap(it)
                3 -> addingHashMap(it)
                4 -> searchingHashMap(it)
                5 -> removingHashMap(it)
            }
        }
        isRunning = false
        isStopped = true
    }

    override fun saveResult() {
        Callback.Result.positionsMaps.addAll(positions)
    }

    private fun finishing(index: Int, time: Int) {
        positions += index
        items[index].changeResult(newResult = time / elementsAmount)
        items[index].updateText()
        items[index].changeBar(true)
        saveResult()
    }

    private fun changeBar(index: Int, stop: Boolean = false) {
        items[index].changeBar(stop)
        positions += index
        saveResult()
    }

    private fun changeAllBars(stop: Boolean = false) {
        repeat(items.size) {
            changeBar(it, stop)
        }
    }

    fun createTreeMap(collectionSize: Int = this.collectionSize): TreeMap<Int, Byte> {
        val treeMap = TreeMap<Int, Byte>()
        repeat(collectionSize) {
            treeMap[it] = 0
        }
        return treeMap
    }

    fun createHashMap(collectionSize: Int = this.collectionSize): HashMap<Int, Byte> {
        val hashMap = HashMap<Int, Byte>()
        repeat(collectionSize) {
            hashMap[it] = 0
        }
        return hashMap
    }

    fun addingTreeMap(index: Int) {
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        var newKey = map!!.size + 1
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!![newKey] = 0
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++newKey
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

    fun searchingTreeMap(index: Int) {
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!!.containsKey(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

    fun removingTreeMap(index: Int) {
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        var key = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!!.remove(key)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++key
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

    fun addingHashMap(index: Int) {
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        var newKey = map!!.size + 1
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!![newKey] = 0
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++newKey
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

    fun searchingHashMap(index: Int) {
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!!.containsKey(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

    fun removingHashMap(index: Int) {
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        var key = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                map = null
                return
            }
            val starting = System.currentTimeMillis()
            map!!.remove(key)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++key
        }
        map!!.clear()
        map = null
        finishing(index, time)
    }

}
