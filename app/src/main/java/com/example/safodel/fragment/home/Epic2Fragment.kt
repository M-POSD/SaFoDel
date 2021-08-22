package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic2Binding
import com.example.safodel.fragment.BasicFragment

class Epic2Fragment : BasicFragment<FragmentEpic2Binding>(FragmentEpic2Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic2Binding.inflate(inflater,container,false)

        setDefaultView()

        return binding.root
    }

    private fun setDefaultView() {
        binding.info1Card.editText.text = "Info 1"
        binding.info2Card.editText.text = "Info 2"
        binding.info3Card.editText.text = "Info 3"
        binding.info4Card.editText.text = "Info 4"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}