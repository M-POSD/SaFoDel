package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.databinding.FragmentInfo3Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data

class Info2Fragment : BasicFragment<FragmentInfo3Binding>(FragmentInfo3Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var info2s: MutableList<GroupCard1Data>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo3Binding.inflate(inflater, container, false)
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
        binding.info2.currentPageText.text = "E-Bikes Rules & Regulation"
        binding.info2.notification.text =
            "Wondering whether the e-bike rules and regulations are different?"
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard1Adapter(requireActivity(), getInfo2s())

        binding.info2.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.info2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info2.recyclerView.layoutManager = layoutManager
    }

    // get the info3s from the model class
    private fun getInfo2s(): MutableList<GroupCard1Data> {
        info2s = GroupCard1Data.init()
        var i = 0
        while (i < info2s.size) {
            when (info2s[i].dataType) {
                "ebikeinfo3" -> i++
                else -> info2s.removeAt(i)
            }
        }
        return info2s
    }

}
