package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.Info1Adapter
import com.example.safodel.adapter.Tip1Adapter
import com.example.safodel.databinding.FragmentInfo1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Info1
import com.example.safodel.model.Tip1

class Info1Fragment : BasicFragment<FragmentInfo1Binding>(FragmentInfo1Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var infos: MutableList<Info1>
    private lateinit var adapter: Info1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo1Binding.inflate(inflater,container,false)
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
        infos = Info1.initializeResultList()
        adapter = Info1Adapter(requireActivity(), infos)

        binding.info1.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.info1.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info1.recyclerView.layoutManager = layoutManager
    }

}
