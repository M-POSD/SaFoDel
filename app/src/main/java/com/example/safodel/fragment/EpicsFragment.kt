package com.example.safodel.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.safodel.R
import com.example.safodel.adapter.EpicViewAdapter
import com.example.safodel.databinding.FragmentEpicsBinding
import com.example.safodel.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


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
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.epic1_name)))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.epic2_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.epic3_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.epic4_name))
        tabLayout.setBackgroundResource(R.color.deep_green)
    }

    private fun getOnTabSelectedListener(): OnTabSelectedListener {
        return object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPage2.currentItem = tab.position
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
                binding.tabbar.notification.text = getString(R.string.epic1_slang)
            }
            1 -> {
                binding.tabbar.notification.text = getString(R.string.epic2_slang)
            }
            2 -> {
                binding.tabbar.notification.text = getString(R.string.epic3_slang)
            }
            3 -> {
                binding.tabbar.notification.text = getString(R.string.epic4_slang)
            }
        }

    }

}