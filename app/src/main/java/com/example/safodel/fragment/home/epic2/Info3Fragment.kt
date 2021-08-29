package com.example.safodel.fragment.home.epic2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.GroupCard2Adapter
import com.example.safodel.databinding.FragmentInfo3Binding
import com.example.safodel.fragment.BasicFragment

class Info3Fragment : BasicFragment<FragmentInfo3Binding>(FragmentInfo3Binding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var info1s: MutableList<EBikeInfo1>
    private lateinit var adapter: GroupCard2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfo3Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root

        binding.info3.extremeSmall.editText.text = "E-bikes Rules & Regulations"
        binding.info3.notification.text =
            "Wondering whether the e-bike rules and regulations are different?"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
    private fun getInfo3s(): MutableList<EBikeInfo1> {
        info1s = EBikeInfo1.init()
        var i = 0
        while (i < info1s.size) {
            when (info1s[i].info_name) {
                "Rule" -> i++
                else -> info1s.removeAt(i)
            }
        }
        return info1s
    }

}
