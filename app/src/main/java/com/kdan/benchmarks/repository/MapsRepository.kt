package com.kdan.benchmarks.repository

import android.app.Activity
import com.kdan.benchmarks.ui.MapsInterface
import com.kdan.benchmarks.viewmodel.MapsViewModel
import java.util.TreeMap

object MapsRepository: MapsInterface {

    private var fakeMap: MutableMap<Int, Byte>? = null
    private val map get() = fakeMap!!
    private var index = 0
    override var viewModel = MapsViewModel()
    private val elementsAmount get() = viewModel.elementsAmount
    private val collectionSize get() = viewModel.collectionSize


    // init { fakeMap = createMap(viewModel.collectionSize) }

    fun startAll() {
        Thread {
            fakeMap = createMap()
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
            fakeMap = null
        }.start()
    }

    private fun finishing(index: Int, sumOfTime: Int) {
        Activity().runOnUiThread {
            viewModel.changeBar(index, true)
            viewModel.changeResult(index, newResult = sumOfTime / viewModel.elementsAmount)
            viewModel.updateText(index)
            if (index == 5) {
                viewModel.stop = true
                viewModel.changeButtonName()
                this.index = 0
            }
        }
    }

    private fun createMap(): MutableMap<Int, Byte> {
        val map = mutableMapOf<Int, Byte>()
        var count = 0
        repeat(collectionSize) {
            map[count] = 0
            ++count
        }
        return map
    }

    private fun MutableMap<Int, Byte>.toTreeMap(): TreeMap<Int, Byte> {
        val keys = this.keys
        val values = this.values.toMutableList()
        var count = 0
        val treeMap = TreeMap<Int, Byte> ()
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
        val hashMap = HashMap<Int, Byte> ()
        keys.forEach {
            hashMap[it] = values[count]
            ++count
        }
        return hashMap
    }

    private fun addingTreeMap(index: Int) {
        val map: TreeMap<Int, Byte> = map.toTreeMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (viewModel.stop) return
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
        if (viewModel.stop) return
        val map: TreeMap<Int, Byte> = map.toTreeMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (viewModel.stop) return
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
        if (viewModel.stop) return
        val map: TreeMap<Int, Byte> = map.toTreeMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (viewModel.stop) return
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
        if (viewModel.stop) return
        val map: HashMap<Int, Byte> = map.toHashMap()
        var sumOfTime = 0
        var newKey = map.size + 1
        repeat(elementsAmount) {
            if (viewModel.stop) return
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
        if (viewModel.stop) return
        val map: HashMap<Int, Byte> = map.toHashMap()
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (viewModel.stop) return
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
        if (viewModel.stop) {
            this.index = 0
            return
        }
        val map: HashMap<Int, Byte> = map.toHashMap()
        var sumOfTime = 0
        var key = 0
        repeat(elementsAmount) {
            if (viewModel.stop) {
                map.clear()
                this.index = 0
                return
            }
            val starting = System.currentTimeMillis()
            map.remove(key)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
            ++key
        }
        finishing(index, sumOfTime)
    }

}