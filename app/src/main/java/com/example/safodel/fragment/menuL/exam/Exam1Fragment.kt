package com.example.safodel.fragment.menuL.exam

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.safodel.R
import com.example.safodel.databinding.FragmentExam1Binding
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.GroupCard2Data
import com.example.safodel.model.Question


class Exam1Fragment : BasicFragment<FragmentExam1Binding>(FragmentExam1Binding::inflate),
    View.OnClickListener {
    private lateinit var mQuestions: MutableList<Question>
    private var mCurrentPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExam1Binding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        mQuestions = Question.init()

        setQuestions()
        configTextOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getArgument() {
        var arg = arguments?.get("userName") ?: "No Name"
    }

    private fun setQuestions() {
        mCurrentPosition = 1
        val question = mQuestions!![mCurrentPosition - 1]

        configDefaultOptionsView()

        binding.progressBar.progress = mCurrentPosition
        binding.progress.text = "$mCurrentPosition/${binding.progressBar.max}"

        binding.question.text = question!!.question
        binding.image.setImageResource(question.image)
        binding.option1.text = question.option1
        binding.option2.text = question.option2
        binding.option3.text = question.option3
        binding.option4.text = question.option4
    }

    private fun configDefaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.option1)
        options.add(1, binding.option2)
        options.add(2, binding.option3)
        options.add(3, binding.option4)

        for (option in options) {
            option.setTextColor(ContextCompat.getColor(requireActivity(), R.color.gray2))
            var typeface: Typeface? = ResourcesCompat.getFont(requireActivity(),R.font.notosansjp_bold)
            option.typeface = typeface
            option.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun configTextOnClickListener() {
        binding.option1.setOnClickListener(this)
        binding.option2.setOnClickListener(this)
        binding.option3.setOnClickListener(this)
        binding.option4.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.option1 -> selectedOptionView(binding.option1,1)
            R.id.option2 -> selectedOptionView(binding.option2,2)
            R.id.option3 -> selectedOptionView(binding.option3,3)
            R.id.option4 -> selectedOptionView(binding.option4,4)
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOption: Int) {
        configDefaultOptionsView()
        mSelectedOptionPosition = selectedOption

        textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark))
        var typeface: Typeface? = ResourcesCompat.getFont(requireActivity(),R.font.notosansjp_black)
        textView.typeface = typeface
        textView.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.selected_option_border_bg
        )

    }
}