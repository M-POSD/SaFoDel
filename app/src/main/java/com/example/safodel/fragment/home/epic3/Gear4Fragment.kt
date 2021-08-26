package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.Gear4Adapter
import com.example.safodel.adapter.GearAdapter
import com.example.safodel.databinding.FragmentGear4Binding

import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Gear

class Gear4Fragment : BasicFragment<FragmentGear4Binding>(FragmentGear4Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gears: MutableList<Gear>
    private lateinit var adapter: Gear4Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear4Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.gear4.extremeSmall.editText.text = "Recommendations for the safety gear"
        binding.gear4.notification.text = "??????"

        configRecycleView()
        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = Gear4Adapter(requireActivity(), getGear4s())

        binding.gear4.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.gear4.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.gear4.recyclerView.layoutManager = layoutManager
    }

    // get the gear4s from the model class
    private fun getGear4s(): MutableList<Gear> {
        gears = Gear.init()
        var i = 0
        while (i < gears.size) {
            when (gears[i].info_type) {
                "Recommendations" -> i++
                else -> gears.removeAt(i)
            }
        }
        return gears
    }

}
