package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import com.example.safodel.R
import com.example.safodel.adapter.EpicStyle1Adapter
import com.example.safodel.adapter.EpicStyle2Adapter
import com.example.safodel.databinding.FragmentEpic1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data
import com.example.safodel.model.GroupCard2Data


class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {
    private lateinit var adapter1: EpicStyle2Adapter
    private lateinit var adapter2: EpicStyle1Adapter
    private lateinit var adapter3: EpicStyle2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater, container, false)

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

    private fun configSection1() {
        binding.tip1Heading.text = getString(R.string.tip1_name_v2)
        adapter1 = EpicStyle2Adapter(requireActivity(), getSection1Data())
        binding.viewPager2Section1.adapter = adapter1
        binding.wormDotsIndicatorSection1.setViewPager2(binding.viewPager2Section1)
    }

    private fun configSection2() {
        binding.tip2Heading.text = getString(R.string.tip2_name_v2)
        adapter2 = EpicStyle1Adapter(requireActivity(), getSection2Data())
        binding.viewPager2Section2.adapter = adapter2
        binding.wormDotsIndicatorSection2.setViewPager2(binding.viewPager2Section2)
    }

    private fun configSection3() {
        binding.tip3Heading.text = getString(R.string.tip3_name_v2)
        adapter3 = EpicStyle2Adapter(requireActivity(), getSection3Data())
        binding.viewPager2Section3.adapter = adapter3
        binding.wormDotsIndicatorSection3.setViewPager2(binding.viewPager2Section3)
    }

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

    private fun getSection2Data(): MutableList<GroupCard1Data> {
        val data = GroupCard1Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "tip2" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }

    private fun getSection3Data(): MutableList<GroupCard2Data> {
        val data = GroupCard2Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "roadSign" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }
}