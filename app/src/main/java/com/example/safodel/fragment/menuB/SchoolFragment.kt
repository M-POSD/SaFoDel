package com.example.safodel.fragment.menuB

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentSchoolBinding
import com.example.safodel.fragment.BasicFragment


class SchoolFragment : BasicFragment<FragmentSchoolBinding>(FragmentSchoolBinding::inflate){
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