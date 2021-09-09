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
import com.example.safodel.databinding.FragmentInfo3Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data

class Info3Fragment : BasicFragment<FragmentInfo3Binding>(FragmentInfo3Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var info3s: MutableList<GroupCard2Data>
    private lateinit var adapter: GroupCard2Adapter

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
        binding.info3.currentPageText.text = "Risks with E-bikes"
        binding.info3.notification.text = "Be aware of the risks of delivering on an e-bike"
    }

    // call recycle view adapter to set up the view
    private fun configRecycleView() {
        adapter = GroupCard2Adapter(requireActivity(), getInfo3s())

        binding.info3.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.info3.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info3.recyclerView.layoutManager = layoutManager
    }

    // get the info3s from the model class
    private fun getInfo3s(): MutableList<GroupCard2Data> {
        info3s = GroupCard2Data.init()
        var i = 0
        while (i < info3s.size) {
            when (info3s[i].dataType) {
                "eBikeInfo3" -> i++
                else -> info3s.removeAt(i)
            }
        }
        return info3s
    }

}
