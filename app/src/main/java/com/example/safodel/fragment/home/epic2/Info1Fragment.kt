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
import com.example.safodel.databinding.FragmentInfo1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Info

class Info1Fragment : BasicFragment<FragmentInfo1Binding>(FragmentInfo1Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var infos: MutableList<Info>
    private lateinit var adapter: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo1Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.info1.extremeSmall.editText.text = "Risks with E-bikes"
        binding.info1.notification.text = "Be aware of RISKs of delivering on e-bikes"

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
    private fun getInfo1s(): MutableList<Info> {
        infos = Info.init()
        var i = 0
        while (i < infos.size) {
            when (infos[i].info_name) {
                "Risk" -> i++
                else -> infos.removeAt(i)
            }
        }
        return infos
    }

}