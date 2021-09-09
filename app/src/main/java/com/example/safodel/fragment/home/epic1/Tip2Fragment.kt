package com.example.safodel.fragment.home.epic1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.databinding.FragmentTip2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data

class Tip2Fragment : BasicFragment<FragmentTip2Binding>(FragmentTip2Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tip2s: MutableList<GroupCard1Data>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip2Binding.inflate(inflater, container, false)
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
        binding.tip2.currentPageText.text = "Delivering at night"
        binding.tip2.notification.text = "Worried about your safety while delivering at night? " +
                "Follow these tips to stay safe"
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard1Adapter(requireActivity(), getTip2s())

        binding.tip2.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.tip2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.tip2.recyclerView.layoutManager = layoutManager
    }

    // get tip2s from the model class
    private fun getTip2s(): MutableList<GroupCard1Data> {
        tip2s = GroupCard1Data.init()
        var i = 0
        while (i < tip2s.size) {
            when (tip2s[i].dataType) {
                "tip2" -> i++
                else -> tip2s.removeAt(i)
            }
        }
        return tip2s

    }

}


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode