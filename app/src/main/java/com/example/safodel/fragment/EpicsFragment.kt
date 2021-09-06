package com.example.safodel.fragment

import android.content.Context
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
import androidx.core.os.bundleOf
import com.example.safodel.fragment.menuB.HomeFragmentArgs
import android.content.SharedPreferences
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator


class EpicsFragment : BasicFragment<FragmentEpicsBinding>(FragmentEpicsBinding::inflate) {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPage2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpicsBinding.inflate(inflater, container, false)

        tabLayout = binding.tabbar.tabLayout
        viewPage2 = binding.tabbar.viewPager2

        val fm: FragmentManager = (activity as MainActivity).supportFragmentManager
        viewPage2.adapter = EpicViewAdapter(fm, lifecycle, 4)

        addTab()

        tabLayout.addOnTabSelectedListener(getOnTabSelectedListener())

        viewPage2.registerOnPageChangeCallback(getOnPageChangeCallBack())

        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addTab() {
        tabLayout.addTab(tabLayout.newTab().setText("Ride safer"))
        tabLayout.addTab(tabLayout.newTab().setText("Delivery on E-bike"))
        tabLayout.addTab(tabLayout.newTab().setText("Safety gear"))
        tabLayout.addTab(tabLayout.newTab().setText("In an Accident"))
        tabLayout.setBackgroundResource(R.color.deep_green)
    }

    private fun getOnTabSelectedListener(): OnTabSelectedListener {
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

    private fun getOnPageChangeCallBack(): ViewPager2.OnPageChangeCallback {
        Log.d("getOnPageChangeCallBack", "getOnPageChangeCallBack successfully")
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("onPageSelected", "onPageSelected successfully")

                when (getInitialPosition()) {
                    "0" -> {
                        updateTabView(0)
                    }
                    "1" -> {
                        updateTabView(1)
                    }
                    "2" -> {
                        updateTabView(2)
                    }
                    "3" -> {
                        updateTabView(3)
                    }
                    else -> updateTabView(position)
                }

                // set the initial position to null
                cleanInitPosition(-1)

            }
        }
    }

    // clean the initial tab position received from the home page
    private fun cleanInitPosition(position: Int) {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "epicPosition", Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putString("epicPosition", "" + position)
        spEditor.apply()
    }

    // get the button position clicked in the previous page to match the tab selected this page
    private fun getInitialPosition(): String? {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "epicPosition",
            Context.MODE_PRIVATE
        )
        return sharedPref.getString("epicPosition", "")
    }

    // update the tab view according to the tab position selected
    private fun updateTabView(position: Int) {
        tabLayout.selectTab(tabLayout.getTabAt(position))

        when (position) {
            0 -> {
                binding.tabbar.notification.text =
                    "Find out how to ride safely while delivering food"
            }
            1 -> {
                binding.tabbar.notification.text =
                    "Find information on using E-Bikes for food delivery"
            }
            2 -> {
                binding.tabbar.notification.text =
                    "Find out the cycling gear you need to deliver safe"
            }
            3 -> {
                binding.tabbar.notification.text =
                    "Find out the measure for handling the bike accident"
            }
        }

    }

}