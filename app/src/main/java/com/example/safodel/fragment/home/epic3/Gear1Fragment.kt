package com.example.safodel.fragment.home.epic3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.Gear1Adapter
import com.example.safodel.databinding.FragmentGear1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Gear1

class Gear1Fragment : BasicFragment<FragmentGear1Binding>(FragmentGear1Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var gear1s: MutableList<Gear1>
    private lateinit var a1Adapter: Gear1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGear1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        gear1s = Gear1.initializeResultList()
        a1Adapter = Gear1Adapter(requireActivity(), gear1s)

        binding.gear1.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.gear1.recyclerView.adapter = a1Adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.gear1.recyclerView.layoutManager = layoutManager
    }

}
