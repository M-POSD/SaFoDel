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

        binding.info2.extremeSmall.editText.text = "Advantages of E-bikes delivering"
        binding.info2.notification.text = "E-bikes are rising in popularity especially in the food delivery community, here's why"

        configRecycleView()

        setToolbarReturn(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        adapter = InfoAdapter(requireActivity(), getInfo2s())

        binding.info2.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )

        binding.info2.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.info2.recyclerView.layoutManager = layoutManager
    }

    // get the info2s from the model class
    private fun getInfo2s() : MutableList<Info> {
        infos = Info.init()
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
