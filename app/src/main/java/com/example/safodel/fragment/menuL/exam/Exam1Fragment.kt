package com.example.safodel.fragment.menuL.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.safodel.databinding.FragmentExam1Binding
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment


class Exam1Fragment : BasicFragment<FragmentExam1Binding>(FragmentExam1Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExam1Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        var arg = arguments?.get("userName") ?: "No Name"
        binding.textView.text = "Welcome $arg\nThis is 1st page of quiz"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}