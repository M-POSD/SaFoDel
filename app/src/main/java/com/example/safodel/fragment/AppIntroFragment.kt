package com.example.safodel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.safodel.R
import com.example.safodel.databinding.FragmentAppIntroBinding
import com.example.safodel.databinding.FragmentHomeBinding


class AppIntroFragment : BasicFragment<FragmentAppIntroBinding>(FragmentAppIntroBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppIntroBinding.inflate(inflater,container,false)
        binding.appIntro.text = "Topic: Educating Food Delivery Bike Riders\n" +
                "\n" +
                "Project: SaFoDel\n" +
                "\n" +
                "Audience: Potential and already existing food delivery bike riders"
        val toolbar = binding.toolbar.root
        setToolbar2(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}