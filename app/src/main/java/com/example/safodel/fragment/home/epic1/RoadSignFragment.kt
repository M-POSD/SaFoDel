package com.example.safodel.fragment.home.epic1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.adapter.GroupCard2Adapter
import com.example.safodel.databinding.FragmentRoadSignBinding
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class RoadSignFragment : BasicFragment<FragmentRoadSignBinding>(FragmentRoadSignBinding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var roadSigns: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoadSignBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        configDefaultTextView()

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // set up the default text view
    private fun configDefaultTextView() {
        binding.roadSign.currentPageText.text = getString(R.string.tip3_name_v2)
        binding.roadSign.notification.text = getString(R.string.tip3_slang)
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getRoadSigns())

        binding.roadSign.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.roadSign.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.roadSign.recyclerView.layoutManager = layoutManager
    }

    // get roadSigns from the model class
    private fun getRoadSigns(): MutableList<GroupCard2Data> {
        roadSigns = GroupCard2Data.init()
        var i = 0
        while (i < roadSigns.size) {
            when (roadSigns[i].dataType) {
                "roadSign" -> i++
                else -> roadSigns.removeAt(i)
            }
        }
        return roadSigns
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode