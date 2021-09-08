package com.example.safodel.fragment.home.epic4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.databinding.FragmentAccident3Binding
import com.example.safodel.databinding.FragmentTip2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data

class Accident3Fragment : BasicFragment<FragmentAccident3Binding>(FragmentAccident3Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var accident3s: MutableList<GroupCard1Data>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccident3Binding.inflate(inflater, container, false)
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
        binding.accident3.currentPageText.text = "Claiming Insurance"
        binding.accident3.notification.text = ""
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard1Adapter(requireActivity(), getAccident3s())

        binding.accident3.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.accident3.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.accident3.recyclerView.layoutManager = layoutManager
    }

    // get accident3s from the model class
    private fun getAccident3s(): MutableList<GroupCard1Data> {
        accident3s = GroupCard1Data.init()
        var i = 0
        while (i < accident3s.size) {
            when (accident3s[i].dataType) {
                "inAnAccident3" -> i++
                else -> accident3s.removeAt(i)
            }
        }
        return accident3s

    }

}


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode