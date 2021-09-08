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
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Accident1Fragment : BasicFragment<FragmentAccident1Binding>(FragmentAccident1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var accident1s: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccident1Binding.inflate(inflater, container, false)
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
        binding.accident1.currentPageText.text = "Steps to Follow"
        binding.accident1.notification.text = ""
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getAccident1s())

        binding.accident1.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.accident1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.accident1.recyclerView.layoutManager = layoutManager
    }

    // get accident1s from the model class
    private fun getAccident1s(): MutableList<GroupCard2Data> {
        accident1s = GroupCard2Data.init()
        var i = 0
        while (i < accident1s.size) {
            when (accident1s[i].dataType) {
                "inAnAccident1" -> i++
                else -> accident1s.removeAt(i)
            }
        }
        return accident1s
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode