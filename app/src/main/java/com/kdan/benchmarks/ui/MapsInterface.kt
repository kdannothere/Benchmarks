package com.kdan.benchmarks.ui

import com.kdan.benchmarks.viewmodel.MapsViewModel

interface MapsInterface {
    val viewModel: MapsViewModel
        get() = MapsViewModel()
}