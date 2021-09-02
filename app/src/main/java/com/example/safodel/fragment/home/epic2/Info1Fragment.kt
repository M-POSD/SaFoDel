package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard2Adapter
import com.example.safodel.databinding.FragmentInfo1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Info1Fragment : BasicFragment<FragmentInfo1Binding>(FragmentInfo1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var info1s: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo1Binding.inflate(inflater, container, false)
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
        binding.info1.currentPageText.text = "Risks with E-bikes"
        binding.info1.notification.text = "Be aware of RISKs of delivering on e-bikes"
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getInfo1s())

        binding.info1.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.info1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info1.recyclerView.layoutManager = layoutManager
    }

    // get the info1s from the model class
    private fun getInfo1s(): MutableList<GroupCard2Data> {
        info1s = GroupCard2Data.init()
        var i = 0
        while (i < info1s.size) {
            when (info1s[i].dataType) {
                "ebikeinfo1" -> i++
                else -> info1s.removeAt(i)
            }
        }
        return info1s
    }

}
