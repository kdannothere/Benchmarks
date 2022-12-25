package com.kdan.benchmarks.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kdan.benchmarks.viewmodel.ItemData
import java.util.TreeMap

class MapsRepository {
    var collectionSize: Int = 0
    var elementsAmount: Int = 0
    var items = MutableLiveData<List<ItemData>>()
    private val itemsAmount by lazy { items.value!!.size }
    var currentOperation = -1
    val isRunning get() = currentOperation >= 0
    var isDone = true
    var temp = mutableSetOf<Int>()

    fun startAll() {
        isDone = false
        currentOperation = 0
        changeAllBars()
        repeat(itemsAmount) {
            Log.d("SHOW", it.toString())
            when (it) {
                0 -> addingTreeMap(it)
                1 -> searchingTreeMap(it)
                2 -> removingTreeMap(it)
                3 -> addingHashMap(it)
                4 -> searchingHashMap(it)
                5 -> removingHashMap(it)
            }
        }
        currentOperation = -1
        isDone = true
    }

    private fun finishing(index: Int, time: Int) {
        temp += index
        items.value!![index].changeResult(newResult = time / elementsAmount)
        items.value!![index].updateText()
        items.value!![index].changeBar(true)
    }

    private fun stopping(index: Int) {
        temp += index
        items.value!![index].changeBar(true)
    }

    private fun changeAllBars(stop: Boolean = false) {
        repeat(itemsAmount) {
            items.value!![it].changeBar(stop)
            temp += it
        }
    }

    private fun createTreeMap(): TreeMap<Int, Byte> {
        val treeMap = TreeMap<Int, Byte>()
        repeat(collectionSize) {
            treeMap[it] = 0
        }
        return treeMap
    }

    private fun createHashMap(): HashMap<Int, Byte> {
        val hashMap = HashMap<Int, Byte>()
        repeat(collectionSize) {
            hashMap[it] = 0
        }
        return hashMap
    }

    private fun addingTreeMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        var newKey = map!!.size + 1
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
                return
            }
            if (it % 100000 == 0) Log.d("SHOW", "it = $it")
            val starting = System.currentTimeMillis()
            map!![newKey] = 0
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++newKey
        }
        map.clear()
        map = null
        finishing(index, time)
    }

    private fun searchingTreeMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
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

    private fun removingTreeMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: TreeMap<Int, Byte>? = createTreeMap()
        var time = 0
        var key = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
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

    private fun addingHashMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        var newKey = map!!.size + 1
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map!![newKey] = 0
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
            ++newKey
        }
        map.clear()
        map = null
        finishing(index, time)
    }

    private fun searchingHashMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
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

    private fun removingHashMap(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        var map: HashMap<Int, Byte>? = createHashMap()
        var time = 0
        var key = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                map!!.clear()
                stopping(index)
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
