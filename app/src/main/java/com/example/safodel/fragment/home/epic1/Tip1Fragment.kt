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
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tip1s: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater, container, false)
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
        binding.tip1.currentPageText.text = getString(R.string.tip1_name_v2)
        binding.tip1.notification.text = getString(R.string.tip1_slang)
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getTip1s())

        binding.tip1.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.tip1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.tip1.recyclerView.layoutManager = layoutManager
    }

    // get tip1s from the model class
    private fun getTip1s(): MutableList<GroupCard2Data> {
        tip1s = GroupCard2Data.init()
        var i = 0
        while (i < tip1s.size) {
            when (tip1s[i].dataType) {
                "tip1" -> i++
                else -> tip1s.removeAt(i)
            }
        }
        return tip1s
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode