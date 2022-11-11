package com.kdan.benchmarks.repository

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kdan.benchmarks.viewmodel.ItemData
import java.util.TreeMap

class MapsRepository {
    var collectionSize: Int = 0
    var elementsAmount: Int = 0
    var items = MutableLiveData<List<ItemData>>()
    private val maps = mutableMapOf<Int, Byte>()
    var stop = false

    fun startAll() {
        Thread {
            Looper.prepare()
            createMaps()
            repeat (6) {
                when (it) {
                    0 -> addingTreeMap(it)
                    1 -> searchingTreeMap(it)
                    2 -> removingTreeMap(it)
                    3 -> addingHashMap(it)
                    4 -> searchingHashMap(it)
                    5 -> removingHashMap(it)
                }
            }
            maps.clear()
        }.start()
    }

    private fun finishing(index: Int, sumOfTime: Int) {
        Log.d("SHOW", "Operation done: $index")
        items.value!![index].changeBar(true)
        items.value!![index].changeResult(newResult = sumOfTime / elementsAmount)
        items.value!![index].updateText()
    }

    private fun stopping(index: Int) {
        Log.d("SHOW", "Operation stopped: $index")
        items.value!![index].changeBar(true)
    }

    private fun addingTreeMap(index: Int) {
        val map: TreeMap<Int, Byte> = maps.toTreeMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map[newKey] = 0
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++newKey
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun searchingTreeMap(index: Int) {
        if (stop) {
            stopping(index)
            return
        }
        val map: TreeMap<Int, Byte> = maps.toTreeMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map.containsKey(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun removingTreeMap(index: Int) {
        if (stop) {
            stopping(index)
            return
        }
        val map: TreeMap<Int, Byte> = maps.toTreeMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map.remove(key)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++key
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun addingHashMap(index: Int) {
        if (stop) {
            stopping(index)
            return
        }
        val map: HashMap<Int, Byte> = maps.toHashMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map[newKey] = 0
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++newKey
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun searchingHashMap(index: Int) {
        if (stop) {
            stopping(index)
            return
        }
        val map: HashMap<Int, Byte> = maps.toHashMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map.containsKey(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun removingHashMap(index: Int) {

        if (stop) {
            stopping(index)
            return
        }
        val map: HashMap<Int, Byte> = maps.toHashMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (stop) {
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            map.remove(key)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++key
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun createMaps() {
        var count = 0
        repeat(collectionSize) {
            maps[count] = 0
            ++count
        }
    }

    private fun MutableMap<Int, Byte>.toTreeMap(): TreeMap<Int, Byte> {
        val keys = this.keys
        val values = this.values.toMutableList()
        var count = 0
        val treeMap = TreeMap<Int, Byte>()
        keys.forEach {
            treeMap[it] = values[count]
            ++count
        }
        return treeMap
    }

    private fun MutableMap<Int, Byte>.toHashMap(): HashMap<Int, Byte> {
        val keys = this.keys
        val values = this.values.toMutableList()
        var count = 0
        val hashMap = HashMap<Int, Byte>()
        keys.forEach {
            hashMap[it] = values[count]
            ++count
        }
        return hashMap
    }

    fun changeAllBars(stop: Boolean = false) {
        repeat(6) {
            items.value?.get(it)?.changeBar(stop)
        }
    }

}
