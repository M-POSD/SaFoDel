package com.example.safodel.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.safodel.R
import com.example.safodel.adapter.EpicViewAdapter
import com.example.safodel.databinding.FragmentEpicsBinding
import com.example.safodel.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import androidx.core.content.ContextCompat




class EpicsFragment : BasicFragment<FragmentEpicsBinding>(FragmentEpicsBinding::inflate) {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPage2 : ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpicsBinding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        tabLayout = binding.tabbar.tabLayout
        viewPage2 = binding.tabbar.viewPager2

        val fm : FragmentManager = (activity as MainActivity).supportFragmentManager
        viewPage2.adapter = EpicViewAdapter(fm, lifecycle,3)

        addTab()

        tabLayout.addOnTabSelectedListener(getOnTabSelectedListener())

        viewPage2.registerOnPageChangeCallback(getOnPageChangeCallBack())

        setToolbarReturn(toolbar)
        return binding.root
    }

    private fun addTab() {
        tabLayout.addTab(tabLayout.newTab().setText("Ride safe"))
        tabLayout.addTab(tabLayout.newTab().setText("E-Bike Delivery"))
        tabLayout.addTab(tabLayout.newTab().setText("Safety gear"))
        tabLayout.setBackgroundResource(R.color.deep_green)
    }

    private fun getOnTabSelectedListener() : OnTabSelectedListener {
        Log.d("getOnTabSelectedListener", "getOnTabSelectedListener successfully")
        return object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPage2.currentItem = tab.position
                Log.d("viewPage2.currentItem", viewPage2.currentItem.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        }
    }

    private fun getOnPageChangeCallBack() : ViewPager2.OnPageChangeCallback {
        Log.d("getOnPageChangeCallBack", "getOnPageChangeCallBack successfully")
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("onPageSelected", "onPageSelected successfully")
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}