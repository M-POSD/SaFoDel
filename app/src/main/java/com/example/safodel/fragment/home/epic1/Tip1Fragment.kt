package com.example.safodel.fragment.home.epic1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater,container,false)
        binding.text1.text = "Here are a few safety tips to follow when delivering food on a bike"
        binding.text2.text = "Worried about your safety while delivering at night? Follow these tips to stay safe."
        val toolbar = binding.toolbar.root
        val mainActivity = activity as MainActivity
        mainActivity.isBottomNavigationVisible(false)
        setToolbarCancel(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}