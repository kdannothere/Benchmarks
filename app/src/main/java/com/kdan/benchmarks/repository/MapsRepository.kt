package com.kdan.benchmarks.repository

import com.kdan.benchmarks.ui.MapsFragment
import java.util.TreeMap

class MapsRepository(
    collectionSize: Int,
    private val elementsAmount: Int,
    private val fragment: MapsFragment,
) {

    private var _map: MutableMap<Int, Int>? = createMap(collectionSize)
    private val map get() = _map!!
    private var index = 0


    init {
        _map = createMap(collectionSize)
    }

    fun startAll() {
        Thread {
            while (index < 6) {
                when (index) {
                    0 -> addingTreeMap(index)
                    1 -> searchingTreeMap(index)
                    2 -> removingTreeMap(index)
                    3 -> addingHashMap(index)
                    4 -> searchingHashMap(index)
                    5 -> removingHashMap(index)
                }
                ++index
            }
            _map = null
        }.start()
    }

    private fun finishing(index: Int, sumOfTime: Int) {
        fragment.activity?.runOnUiThread {
            fragment.viewModel.changeBar(index)
            fragment.viewModel.changeResult(index, newResult = sumOfTime / elementsAmount)
            fragment.updateText(index)
            fragment.recyclerView.adapter?.notifyItemChanged(index)
            if (index == 5) {
                fragment.countClicks = 0
                fragment.changeButtonName()
            }
        }
    }

    private fun createMap(collectionSize: Int): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        var count = 0
        repeat(collectionSize) {
            map[count] = 0
            ++count
        }
        return map
    }

    private fun MutableMap<Int, Int>.toTreeMap(): TreeMap<Int, Int> {
        val keys = this.keys
        val values = this.values.toMutableList()
        var count = 0
        val treeMap = TreeMap<Int, Int> ()
        keys.forEach {
            treeMap[it] = values[count]
            ++count
        }
        return treeMap
    }

    private fun MutableMap<Int, Int>.toHashMap(): HashMap<Int, Int> {
        val keys = this.keys
        val values = this.values.toMutableList()
        var count = 0
        val hashMap = HashMap<Int, Int> ()
        keys.forEach {
            hashMap[it] = values[count]
            ++count
        }
        return hashMap
    }

    private fun addingTreeMap(index: Int) {
        val map: TreeMap<Int, Int> = map.toTreeMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map[newKey] = 0
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
            ++newKey
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun searchingTreeMap(index: Int) {
        val map: TreeMap<Int, Int> = map.toTreeMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map.containsKey(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun removingTreeMap(index: Int) {
        val map: TreeMap<Int, Int> = map.toTreeMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map.remove(key)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
            ++key
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun addingHashMap(index: Int) {
        val map: HashMap<Int, Int> = map.toHashMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map[newKey] = 0
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
            ++newKey
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun searchingHashMap(index: Int) {
        val map: HashMap<Int, Int> = map.toHashMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map.containsKey(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
        }
        map.clear()
        finishing(index, sumOfTime)
    }

    private fun removingHashMap(index: Int) {
        val map: HashMap<Int, Int> = map.toHashMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            map.remove(key)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
            ++key
        }
        map.clear()
        finishing(index, sumOfTime)
    }

}



























