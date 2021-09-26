package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import com.example.safodel.R
import com.example.safodel.adapter.EpicStyle1Adapter
import com.example.safodel.adapter.EpicStyle2Adapter
import com.example.safodel.databinding.FragmentEpic4Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data
import com.example.safodel.model.GroupCard2Data


class Epic4Fragment : BasicFragment<FragmentEpic4Binding>(FragmentEpic4Binding::inflate) {
    private lateinit var adapter1: EpicStyle2Adapter
    private lateinit var adapter2: EpicStyle2Adapter
    private lateinit var adapter3: EpicStyle1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic4Binding.inflate(inflater, container, false)

        configSection1()
        configSection2()
        configSection3()

        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 1. set the view for step to follow
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configSection1() {
        binding.heading1.text = getString(R.string.in_an_accident1_name)
        adapter1 = EpicStyle2Adapter(requireActivity(), getSection1Data())
        binding.viewPager2Section1.adapter = adapter1
        binding.wormDotsIndicatorSection1.setViewPager2(binding.viewPager2Section1)
    }

    /**
     * 1. set the view for workers' rights
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configSection2() {
        binding.heading2.text = getString(R.string.in_an_accident2_name)
        adapter2 = EpicStyle2Adapter(requireActivity(), getSection2Data())
        binding.viewPager2Section2.adapter = adapter2
        binding.wormDotsIndicatorSection2.setViewPager2(binding.viewPager2Section2)
    }

    /**
     * 1. set the view for claiming insurance
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configSection3() {
        binding.heading3.text = getString(R.string.in_an_accident3_name)
        adapter3 = EpicStyle1Adapter(requireActivity(), getSection3Data())
        binding.viewPager2Section3.adapter = adapter3
        binding.wormDotsIndicatorSection3.setViewPager2(binding.viewPager2Section3)
    }

    /**
     * get the first section data
     */
    private fun getSection1Data(): MutableList<GroupCard2Data> {
        val data = GroupCard2Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "inAnAccident1" -> i++
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
                "inAnAccident2" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }

    /**
     * get the thrid section data
     */
    private fun getSection3Data(): MutableList<GroupCard1Data> {
        val data = GroupCard1Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "inAnAccident3" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }
}