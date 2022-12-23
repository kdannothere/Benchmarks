package com.kdan.benchmarks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kdan.benchmarks.databinding.ActivityMainBinding
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.ui.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, index ->
            tab.text = if (index == 0) getString(R.string.tab_collections) else getString(R.string.tab_maps)
        }.attach()
        binding.floatingButton.setOnClickListener {
            val dialog = CollectionSizeDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
