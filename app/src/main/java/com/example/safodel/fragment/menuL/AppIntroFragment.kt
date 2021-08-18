package com.example.safodel.fragment.menuL

import android.os.Bundle
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
        binding.appIntro.text = "Topic:" + "\n" +
                "Educating Food Delivery Bike Riders\n" + "\n" +
                "Project:" + "\n" +
                "SaFoDel\n" + "\n" +
                "Audience:" + "\n" +
                "Potential and already existing food delivery bike riders"
        val toolbar = binding.toolbar.root
        setToolbar2(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}