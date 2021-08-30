package com.example.safodel.fragment.menuL.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentExam1Binding
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data
import com.example.safodel.model.Question


class Exam1Fragment : BasicFragment<FragmentExam1Binding>(FragmentExam1Binding::inflate) {
    private lateinit var questions: MutableList<Question>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExam1Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)


        questions = Question.init()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getArgument() {
        var arg = arguments?.get("userName") ?: "No Name"
        binding.textView.text = "Welcome $arg\nThis is 1st page of quiz"
    }
}