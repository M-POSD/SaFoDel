package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentAnalysisBinding
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment


class AnalysisFragment : BasicFragment<FragmentAnalysisBinding>(FragmentAnalysisBinding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}