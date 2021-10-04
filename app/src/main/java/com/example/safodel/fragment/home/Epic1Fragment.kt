package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import com.example.safodel.R
import com.example.safodel.adapter.EpicStyle2Adapter
import com.example.safodel.databinding.FragmentEpic1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {
    private lateinit var adapter1: EpicStyle2Adapter
    private lateinit var adapter2: EpicStyle2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater, container, false)

        configSection1()
        configSection2()

        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 1. set the view for safety tips
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configSection1() {
        binding.heading1.text = getString(R.string.tip1_name)
        adapter1 = EpicStyle2Adapter(requireActivity(), getSection1Data())
        binding.viewPager2Section1.adapter = adapter1
        binding.wormDotsIndicatorSection1.setViewPager2(binding.viewPager2Section1)
    }

    /**
     * 1. set the view for delivering at night
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configSection2() {
        binding.heading2.text = getString(R.string.tip2_name)
        adapter2 = EpicStyle2Adapter(requireActivity(), getSection2Data())
        binding.viewPager2Section2.adapter = adapter2
        binding.wormDotsIndicatorSection2.setViewPager2(binding.viewPager2Section2)
    }

    /**
     * get the first section data
     */
    private fun getSection1Data(): MutableList<GroupCard2Data> {
        val data = GroupCard2Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "tip1" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }

    /**
     * get the second section data
     */
    private fun getSection2Data(): MutableList<GroupCard2Data> {
        val data = GroupCard2Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "tip2" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }
}