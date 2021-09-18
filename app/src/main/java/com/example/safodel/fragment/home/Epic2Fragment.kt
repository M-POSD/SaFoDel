package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import com.example.safodel.R
import com.example.safodel.adapter.EpicStyle1Adapter
import com.example.safodel.adapter.EpicStyle2Adapter
import com.example.safodel.databinding.FragmentEpic2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data
import com.example.safodel.model.GroupCard2Data


class Epic2Fragment : BasicFragment<FragmentEpic2Binding>(FragmentEpic2Binding::inflate) {
    private lateinit var adapter1: EpicStyle1Adapter
    private lateinit var adapter2: EpicStyle1Adapter
    private lateinit var adapter3: EpicStyle2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic2Binding.inflate(inflater, container, false)

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
        binding.tip1Heading.text = getString(R.string.info1_name)
        adapter1 = EpicStyle1Adapter(requireActivity(), getSection1Data())
        binding.viewPager2Section1.adapter = adapter1
        binding.wormDotsIndicatorSection1.setViewPager2(binding.viewPager2Section1)
    }

    private fun configSection2() {
        binding.tip2Heading.text = getString(R.string.info2_name)
        adapter2 = EpicStyle1Adapter(requireActivity(), getSection2Data())
        binding.viewPager2Section2.adapter = adapter2
        binding.wormDotsIndicatorSection2.setViewPager2(binding.viewPager2Section2)
    }

    private fun configSection3() {
        binding.tip3Heading.text = getString(R.string.info3_name)
        adapter3 = EpicStyle2Adapter(requireActivity(), getSection3Data())
        binding.viewPager2Section3.adapter = adapter3
        binding.wormDotsIndicatorSection3.setViewPager2(binding.viewPager2Section3)
    }

    private fun getSection1Data(): MutableList<GroupCard1Data> {
        val data = GroupCard1Data.init()
        var i = 0
        while (i < data.size) {
            when (data[i].dataType) {
                "eBikeInfo1" -> i++
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
                "eBikeInfo2" -> i++
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
                "eBikeInfo3" -> i++
                else -> data.removeAt(i)
            }
        }
        return data
    }
}