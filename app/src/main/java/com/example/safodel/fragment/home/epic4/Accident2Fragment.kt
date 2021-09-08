package com.example.safodel.fragment.home.epic4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard2Adapter
import com.example.safodel.databinding.FragmentAccident1Binding
import com.example.safodel.databinding.FragmentAccident2Binding
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Accident2Fragment : BasicFragment<FragmentAccident2Binding>(FragmentAccident2Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var accident2s: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccident2Binding.inflate(inflater, container, false)
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
        binding.accident2.currentPageText.text = "Workers' Rights"
        binding.accident2.notification.text = ""
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getAccident2s())

        binding.accident2.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.accident2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.accident2.recyclerView.layoutManager = layoutManager
    }

    // get accident1s from the model class
    private fun getAccident2s(): MutableList<GroupCard2Data> {
        accident2s = GroupCard2Data.init()
        var i = 0
        while (i < accident2s.size) {
            when (accident2s[i].dataType) {
                "inAnAccident2" -> i++
                else -> accident2s.removeAt(i)
            }
        }
        return accident2s
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode