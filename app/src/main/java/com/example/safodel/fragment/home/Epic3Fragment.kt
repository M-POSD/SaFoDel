package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic3Binding
import com.example.safodel.fragment.BasicFragment

class Epic3Fragment : BasicFragment<FragmentEpic3Binding>(FragmentEpic3Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic3Binding.inflate(inflater,container,false)
        setDefaultView()
        return binding.root
    }

    private fun setDefaultView() {
        binding.gear1Card.title.text = "Gear 1"
        binding.gear1Card.subtitle.text = "safety gear should wear while delivering"
        binding.gear2Card.title.text = "Gear 2"
        binding.gear2Card.subtitle.text = "a detailed checklist of necessary safety equipment"
        binding.gear3Card.title.text = "Gear 3"
        binding.gear3Card.subtitle.text = "Australian standards for the safety gear "
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}