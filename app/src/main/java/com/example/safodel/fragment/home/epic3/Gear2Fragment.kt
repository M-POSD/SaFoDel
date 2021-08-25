package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GearAdapter
import com.example.safodel.databinding.FragmentGear2Binding
import com.example.safodel.databinding.FragmentGear3Binding

import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Gear

class Gear2Fragment : BasicFragment<FragmentGear2Binding>(FragmentGear2Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gears: MutableList<Gear>
    private lateinit var adapter: GearAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear2Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        binding.gear2.extremeSmall.editText.text = "A detailed checklist of necessary safety equipment"
        binding.gear2.notification.text = "Prepare all gears listed below??????"

        configRecycleView()
        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = GearAdapter(requireActivity(), getGear3s())

        binding.gear2.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.gear2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.gear2.recyclerView.layoutManager = layoutManager
    }

    private fun getGear3s() : MutableList<Gear> {
        gears = Gear.initializeResultList()
        var i = 0
        while (i < gears.size) {
            when(gears[i].info_type) {
                "Standard" -> i++
                else -> gears.removeAt(i)
            }
        }
        return gears
    }

}
