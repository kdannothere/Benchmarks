package com.kdan.benchmarks.repository

import com.kdan.benchmarks.ui.CollectionsFragment
import kotlinx.coroutines.Runnable
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class CollectionsRepository(
    collectionSize: Int,
    private val elementsAmount: Int,
    private val fragment: CollectionsFragment,
) {

    private var _collection: List<Int>? = createList(collectionSize)
    private val collection get() = _collection!!
    private var index = 0


    init {
        _collection = createList(collectionSize)
    }

    fun startAll() {
        Thread(Runnable {
            while (index < 21) {
                when (index) {
                    0 -> addingBeginningArrayList(index)
                    1 -> addingMiddleArrayList(index)
                    2 -> addingEndArrayList(index)
                    3 -> searchByValueArrayList(index)
                    4 -> removingBeginningArrayList(index)
                    5 -> removingMiddleArrayList(index)
                    6 -> removingEndArrayList(index)
                    7 -> addingBeginningLinkedList(index)
                    8 -> addingMiddleLinkedList(index)
                    9 -> addingEndLinkedList(index)
                    10 -> searchByValueLinkedList(index)
                    11 -> removingBeginningLinkedList(index)
                    12 -> removingMiddleLinkedList(index)
                    13 -> removingEndLinkedList(index)
                    14 -> addingBeginningCopyOnWriteArrayList(index)
                    15 -> addingMiddleCopyOnWriteArrayList(index)
                    16 -> addingEndCopyOnWriteArrayList(index)
                    17 -> searchByValueCopyOnWriteArrayList(index)
                    18 -> removingBeginningCopyOnWriteArrayList(index)
                    19 -> removingMiddleCopyOnWriteArrayList(index)
                    20 -> removingEndCopyOnWriteArrayList(index)
                }
                ++index
            }
            _collection = null
        }).start()
    }

    private fun finishing(index: Int, sumOfTime: Int) {
        fragment.activity?.runOnUiThread {
            fragment.viewModel.changeBar(index)
            fragment.viewModel.changeResult(index, newResult = sumOfTime / elementsAmount)
            fragment.updateText(index)
            fragment.recyclerView.adapter?.notifyItemChanged(index)
            if (index == 20) {
                fragment.countClicks = 0
                fragment.changeButtonName()
            }
        }
    }

    private fun createList(collectionSize: Int): List<Int> {
        return List(collectionSize) { 0 }
    }

    private fun addingBeginningArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
            ++sumOfTime
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingMiddleArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingEndArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun searchByValueArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.binarySearch(0, 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingBeginningArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingMiddleArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingEndArrayList(index: Int) {
        var array: ArrayList<Int>? = collection.toCollection(arrayListOf())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingBeginningLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingMiddleLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingEndLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun searchByValueLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.binarySearch(0, 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingBeginningLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingMiddleLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingEndLinkedList(index: Int) {
        var array: LinkedList<Int>? = collection.toCollection(LinkedList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingBeginningCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingMiddleCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun addingEndCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun searchByValueCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.binarySearch(0, 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingBeginningCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingMiddleCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) return
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

    private fun removingEndCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Int>? = collection.toCollection(CopyOnWriteArrayList())
        var sumOfTime = 0
        repeat(elementsAmount) {
            if (fragment.stop) {
                fragment.countClicks = 0
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            sumOfTime += (ending - starting).toInt()
        }
        finishing(index, sumOfTime)
        array = null
    }

}