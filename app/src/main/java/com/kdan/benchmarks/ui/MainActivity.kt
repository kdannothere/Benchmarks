package com.kdan.benchmarks.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var dialog: DialogFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = getInstanceDialog()
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    getString(R.string.tab_collections)
                }
                1 -> {
                    getString(R.string.tab_maps)
                }
                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
        binding.floatingButton.setOnClickListener { showDialog() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getInstanceDialog(): CollectionSizeDialogFragment {
        return CollectionSizeDialogFragment()
    }

    private fun showDialog() = dialog.show(this.supportFragmentManager, dialog.tag)

}
