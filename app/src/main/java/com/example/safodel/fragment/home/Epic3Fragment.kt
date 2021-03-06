package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import com.example.safodel.adapter.EpicStyle3Adapter
import com.example.safodel.databinding.FragmentEpic3Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data


class Epic3Fragment : BasicFragment<FragmentEpic3Binding>(FragmentEpic3Binding::inflate) {
    private lateinit var adapter1: EpicStyle3Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic3Binding.inflate(inflater, container, false)

        configRoadSignView()

        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 1. set the view for road sign
     * 2. config the view with recycle view adapter
     * 3. connect the indicator view with view pager2
     */
    private fun configRoadSignView() {
        adapter1 = EpicStyle3Adapter(requireActivity(), getRoadSignData())
        binding.viewPager2Section1.adapter = adapter1
        binding.wormDotsIndicatorSection1.setViewPager2(binding.viewPager2Section1)
    }

    /**
     *  get road sign data
     */
    private fun getRoadSignData(): MutableList<GroupCard2Data> {
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