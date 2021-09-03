package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentAppIntroBinding
import com.example.safodel.fragment.BasicFragment


class AppIntroFragment : BasicFragment<FragmentAppIntroBinding>(FragmentAppIntroBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppIntroBinding.inflate(inflater,container,false)
        binding.appIntro.text = "Topic:" + "\n" + "\n" +
                "Educating Food Delivery Bike Riders\n" + "\n" + "\n" +
                "Audience:" + "\n" + "\n" +
                "Potential and already existing food delivery bike riders"
        binding.appIntro.gravity = Gravity.CENTER
        binding.appIntro.textSize = 16F
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}