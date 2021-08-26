package com.example.safodel.fragment.home.epic1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.TipAdapter
import com.example.safodel.databinding.FragmentTip2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Tip

class Tip2Fragment : BasicFragment<FragmentTip2Binding>(FragmentTip2Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tips: MutableList<Tip>
    private lateinit var adapter: TipAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip2Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.tip2.extremeSmall.editText.text = "Delivering at night"
        binding.tip2.notification.text = "Worried about your safety while delivering at night? " +
                "Follow these tips to stay safe"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = TipAdapter(requireActivity(), getTip2s())

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
    private fun getTip2s(): MutableList<Tip> {
        tips = Tip.init()
        var i = 0
        while (i < tips.size) {
            Log.d("getTip1s", tips[i].tip_id.toString())
            when (tips[i].tip_id) {
                2 -> i++
                else -> tips.removeAt(i)
            }
        }
        return tips

    }

}


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode