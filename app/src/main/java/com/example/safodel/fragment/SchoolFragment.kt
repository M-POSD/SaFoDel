package com.example.safodel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.databinding.FragmentSchoolBinding



class SchoolFragment :BasicFragment<FragmentSchoolBinding>(FragmentSchoolBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolBinding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        setToolbar(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}