package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentEpic3Binding
import com.example.safodel.fragment.BasicFragment

class Epic3Fragment : BasicFragment<FragmentEpic3Binding>(FragmentEpic3Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic3Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        binding.gear1Card.editText.text = "Gear 1"
        binding.gear2Card.editText.text = "Gear 2"

        setToolbar2(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}