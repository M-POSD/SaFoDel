package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard1Adapter
import com.example.safodel.adapter.GroupCard2Adapter
import com.example.safodel.databinding.FragmentInfo2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard1Data

class Info2Fragment : BasicFragment<FragmentInfo2Binding>(FragmentInfo2Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var info2s: MutableList<GroupCard1Data>
    private lateinit var adapter: GroupCard1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo2Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.info2.currentPageText.text = "Advantages of E-bikes delivering"
        binding.info2.notification.text =
            "E-bikes are rising in popularity especially in the food delivery community, here's why"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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

    // get the info2s from the model class
    private fun getInfo2s(): MutableList<GroupCard1Data> {
        info2s = GroupCard1Data.init()
        var i = 0
        while (i < info2s.size) {
            when (info2s[i].dataType) {
                "ebikeinfo2" -> i++
                else -> info2s.removeAt(i)
            }
        }
        return info2s
    }

}
