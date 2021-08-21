package com.example.safodel.fragment.home.epic1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.adapter.Tip1Adapter
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Tip1Info
import com.example.safodel.ui.main.MainActivity

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate){
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var tips: MutableList<Tip1Info>
    private lateinit var adapter: Tip1Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        configRecycleView()

        setToolbarCancel(toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configRecycleView() {
        tips = Tip1Info.initializeResultList()
        adapter = Tip1Adapter(requireActivity(), tips)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration( requireActivity(),
                LinearLayoutManager.VERTICAL )
        )
        binding.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
    }

}

//        val mainActivity = activity as MainActivity
//        mainActivity.isBottomNavigationVisible(false)

//        toolbar.setBackgroundResource(R.color.skin) => set toolbar background color not null


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode