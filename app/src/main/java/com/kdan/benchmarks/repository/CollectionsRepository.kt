package com.kdan.benchmarks.repository

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.kdan.benchmarks.viewmodel.ItemData
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class CollectionsRepository {//new
    var collectionSize: Int = 0
    var elementsAmount: Int = 0
    var items = MutableLiveData<List<ItemData>>()
    var updater = MutableLiveData<Boolean>() // updates items in the adapter if value is true
    val isRunning get() = currentOperation >= 0
    var currentOperation = -1
    var temp = 0

    fun startAll() {
        Thread {
            Looper.prepare()
            currentOperation = 0
            changeAllBars()
            repeat(21) {
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
            Thread.sleep(100)
            currentOperation = -1
            updater.postValue(true)
        }.start()
    }

    private fun finishing(index: Int, time: Int) {
        temp = index
        items.value!![index].changeResult(newResult = time / elementsAmount)
        items.value!![index].updateText()
        items.value!![index].changeBar(true)
        updater.postValue(true)
        Thread.sleep(100)
    }

    private fun stopping(index: Int) {
        temp = index
        items.value!![index].changeBar(true)
        updater.postValue(true)
        Thread.sleep(100)
    }

    private fun changeAllBars(stop: Boolean = false) {
        repeat(21) {
            temp = it
            items.value!![it].changeBar(stop)
            updater.postValue(true)
            Thread.sleep(100)
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
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingMiddleArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingEndArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun searchByValueArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.binarySearch("0".toByte(), 0, array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingBeginningArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingMiddleArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingEndArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: ArrayList<Byte> = createArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingBeginningLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingMiddleLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingEndLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun searchByValueLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.binarySearch("0".toByte(), 0, array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingBeginningLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingMiddleLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingEndLinkedList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: LinkedList<Byte> = createLinkedList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingBeginningCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(0, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingMiddleCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.size / 2, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun addingEndCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.add(array.lastIndex, 1)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun searchByValueCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.binarySearch("0".toByte(), 0, array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingBeginningCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(0)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingMiddleCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.size / 2)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

    private fun removingEndCopyOnWriteArrayList(index: Int) {
        if (!isRunning) {
            stopping(index)
            return
        }
        currentOperation = index
        val array: CopyOnWriteArrayList<Byte> = createCopyOnWriteArrayList()
        var time = 0
        repeat(elementsAmount) {
            if (!isRunning) {
                array.clear()
                stopping(index)
                return
            }
            val starting = System.currentTimeMillis()
            array.removeAt(array.lastIndex)
            val ending = System.currentTimeMillis()
            time += (ending - starting).toInt()
        }
        array.clear()
        finishing(index, time)
    }

}