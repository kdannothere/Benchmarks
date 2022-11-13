package com.kdan.benchmarks

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kdan.benchmarks.adapters.ViewPagerAdapter
import com.kdan.benchmarks.databinding.ActivityMainBinding
import com.kdan.benchmarks.fragments.dialog_fragments.CollectionSizeDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fragmentManager: FragmentManager
    private lateinit var dialog: DialogFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = this.supportFragmentManager
        dialog = CollectionSizeDialogFragment()
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    "Collections"
                }
                1 -> {
                    "Maps"
                }
                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()
        dialog.show(fragmentManager, dialog.tag)

    }



}