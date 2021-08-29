package com.example.safodel.fragment.home.epic1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tip1s: MutableList<Tip1>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.tip1.extremeSmall.editText.text = "Riding a bicycle"
        binding.tip1.notification.text = "Here are a few safety tips for delivering food on bicycle"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = GroupCard1Adapter(requireActivity(), getTip1s())

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
    private fun getTip1s(): MutableList<Tip1> {
        tip1s = Tip1.init()
        var i = 0
        while (i < tip1s.size) {
            Log.d("getTip1s", tip1s[i].tip_id.toString())
            when (tip1s[i].tip_id) {
                1 -> i++
                else -> tip1s.removeAt(i)
            }
        }
        return tip1s
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode