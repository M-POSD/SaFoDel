package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.InfoAdapter
import com.example.safodel.databinding.FragmentInfo2Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Info

class Info2Fragment : BasicFragment<FragmentInfo2Binding>(FragmentInfo2Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var infos: MutableList<Info>
    private lateinit var adapter: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo2Binding.inflate(inflater,container,false)
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
        adapter = InfoAdapter(requireActivity(), getInfo1s())

        binding.info2.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.info2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info2.recyclerView.layoutManager = layoutManager
    }

    private fun getInfo1s() : MutableList<Info> {
        infos = Info.initializeResultList()
        var i = 0
        while (i < infos.size) {
            when(infos[i].info_name) {
                "Advantage" -> i++
                else -> infos.removeAt(i)
            }
        }
        return infos
    }

}
