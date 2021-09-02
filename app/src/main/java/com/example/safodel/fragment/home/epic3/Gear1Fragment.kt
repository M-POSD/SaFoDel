package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.databinding.FragmentGear1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data

class Gear1Fragment : BasicFragment<FragmentGear1Binding>(FragmentGear1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gears: MutableList<GroupCard1Data>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear1Binding.inflate(inflater, container, false)
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

    private fun configDefaultTextView() {
        binding.gear1.currentPageText.text = "Gears basic information"
        binding.gear1.notification.text = "Wearing proper safety gear while delivering food is a must. " +
                "Find more about the essential safety gear below. "
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard1Adapter(requireActivity(), getGear1s())

        binding.gear1.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.gear1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.gear1.recyclerView.layoutManager = layoutManager
    }

    // get the gears from the model class
    private fun getGear1s(): MutableList<GroupCard1Data> {
        gears = GroupCard1Data.init()
        var i = 0
        while (i < gears.size) {
            when (gears[i].dataType) {
                "gear" -> i++
                else -> gears.removeAt(i)
            }
        }
        return gears
    }

}
