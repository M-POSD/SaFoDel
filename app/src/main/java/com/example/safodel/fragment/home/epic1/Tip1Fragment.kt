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
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Tip

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tips: MutableList<Tip>
    private lateinit var adapter: TipAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        binding.tip1.extremeSmall.editText.text = "Riding a bicycle"
        binding.tip1.notification.text = "Here are a few safety tips for delivery food on bicycle"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = TipAdapter(requireActivity(), getTip1s())

        binding.tip1.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.tip1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.tip1.recyclerView.layoutManager = layoutManager
    }

    // get tip1s from the model class
    private fun getTip1s() : MutableList<Tip> {
        tips = Tip.init()
        var i = 0
        while (i < tips.size) {
            Log.d("getTip1s", tips[i].tip_id.toString())
            when(tips[i].tip_id) {
                1 -> i++
                else -> tips.removeAt(i)
            }
        }
        return tips
    }

}

// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode