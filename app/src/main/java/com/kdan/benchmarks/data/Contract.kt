package com.kdan.benchmarks.data

import com.kdan.benchmarks.ui.collections.CollsPresenter
import com.kdan.benchmarks.ui.maps.MapsPresenter

interface Contract {

    object SavedState {
        var collsPresenter: CollsPresenter? = null
        var mapsPresenter: MapsPresenter? = null
    }

    interface View {
        fun setupPresenter()
    }

    interface Presenter {
        fun onDestroy()
    }
}