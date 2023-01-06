package com.kdan.benchmarks.repository

import android.util.Log
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.ItemData
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class CollectionsRepository : Callback.SavingResult {
    var collectionSize: Int = 0
    var elementsAmount: Int = 0
    lateinit var items: MutableList<ItemData>
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
                    0 -> addingBeginningArrayList(it)
                    1 -> addingMiddleArrayList(it)
                    2 -> addingEndArrayList(it)
                    3 -> searchByValueArrayList(it)
                    4 -> removingBeginningArrayList(it)
                    5 -> removingMiddleArrayList(it)
                    6 -> removingEndArrayList(it)
                    7 -> addingBeginningLinkedList(it)
                    8 -> addingMiddleLinkedList(it)
                    9 -> addingEndLinkedList(it)
                    10 -> searchByValueLinkedList(it)
                    11 -> removingBeginningLinkedList(it)
                    12 -> removingMiddleLinkedList(it)
                    13 -> removingEndLinkedList(it)
                    14 -> addingBeginningCopyOnWriteArrayList(it)
                    15 -> addingMiddleCopyOnWriteArrayList(it)
                    16 -> addingEndCopyOnWriteArrayList(it)
                    17 -> searchByValueCopyOnWriteArrayList(it)
                    18 -> removingBeginningCopyOnWriteArrayList(it)
                    19 -> removingMiddleCopyOnWriteArrayList(it)
                    20 -> removingEndCopyOnWriteArrayList(it)
            }
        }
        isRunning = false
        isStopped = true
    }

    override fun saveResult() {
        Callback.Result.positionsCollections.addAll(positions)
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

    private fun createArrayList(): ArrayList<Byte> {
        val arrayList = mutableListOf<Byte>()
        repeat(collectionSize) {
            arrayList += "0".toByte()
        }
        return arrayList.toCollection(arrayListOf())
    }

    private fun createLinkedList(): LinkedList<Byte> {
        val linkedList = mutableListOf<Byte>()
        repeat(collectionSize) {
            linkedList += "0".toByte()
        }
        return linkedList.toCollection(LinkedList())
    }

    private fun createCopyOnWriteArrayList(): CopyOnWriteArrayList<Byte> {
        val copyOnWriteArrayList = mutableListOf<Byte>()
        repeat(collectionSize) {
            copyOnWriteArrayList += "0".toByte()
        }
        return copyOnWriteArrayList.toCollection(CopyOnWriteArrayList())
    }

    private fun addingBeginningArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
        finishing(index, time)
    }

    private fun addingMiddleArrayList(index: Int) {
        Log.d("SHOW", "0")
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            if (it % 990000 == 0) Log.d("SHOW", it.toString())
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingEndArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun searchByValueArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.binarySearch("0".toByte(), 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingBeginningArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingMiddleArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingEndArrayList(index: Int) {
        var array: ArrayList<Byte>? = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingBeginningLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingMiddleLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingEndLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun searchByValueLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.binarySearch("0".toByte(), 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingBeginningLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingMiddleLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingEndLinkedList(index: Int) {
        var array: LinkedList<Byte>? = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingBeginningCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingMiddleCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(array!!.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun addingEndCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.add(array!!.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun searchByValueCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.binarySearch("0".toByte(), 0, array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingBeginningCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingMiddleCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

    private fun removingEndCopyOnWriteArrayList(index: Int) {
        var array: CopyOnWriteArrayList<Byte>? = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array!!.clear()
                array = null
                return
            }
            val starting = System.currentTimeMillis()
            array!!.removeAt(array!!.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array!!.clear()
        array = null
        finishing(index, time)
    }

}