package com.example.safodel.fragment.menuL.exam

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.example.safodel.R
import com.example.safodel.databinding.FragmentExam1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Question


class Exam1Fragment : BasicFragment<FragmentExam1Binding>(FragmentExam1Binding::inflate),
    View.OnClickListener {
    private lateinit var mQuestions: MutableList<Question>
    private var mCurrentPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExam1Binding.inflate(inflater, container, false)
        toast = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        mQuestions = Question.init()

        configOnClickListener()
        setQuestions()

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
        binding.submitButton.text = "SUBMIT"

        // when the question haven't answered, set the current questions is clickable until has been answer
        setOptionClickable(true)

        val question = mQuestions!![mCurrentPosition - 1]

        configDefaultOptionsView()

        binding.progressBar.progress = mCurrentPosition
        binding.progress.text = "$mCurrentPosition/${binding.progressBar.max}"

        binding.question.text = question!!.question
        binding.image.setImageResource(question.image)
        binding.opt1.option.text = question.option1
        binding.opt2.option.text = question.option2
        binding.opt3.option.text = question.option3
        binding.opt4.option.text = question.option4
    }

    private fun configDefaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.opt1.option)
        options.add(1, binding.opt2.option)
        options.add(2, binding.opt3.option)
        options.add(3, binding.opt4.option)

        for (option in options) {
            option.setTextColor(ContextCompat.getColor(requireActivity(), R.color.gray2))
            var typeface: Typeface? =
                ResourcesCompat.getFont(requireActivity(), R.font.notosansjp_bold)
            option.typeface = typeface
            option.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun configOnClickListener() {
        binding.opt1.option.setOnClickListener(this)
        binding.opt2.option.setOnClickListener(this)
        binding.opt3.option.setOnClickListener(this)
        binding.opt4.option.setOnClickListener(this)
        binding.submitButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.opt1 -> selectedOptionView(binding.opt1.option, 1)
            R.id.opt2 -> selectedOptionView(binding.opt2.option, 2)
            R.id.opt3 -> selectedOptionView(binding.opt3.option, 3)
            R.id.opt4 -> selectedOptionView(binding.opt4.option, 4)
            R.id.submitButton -> {
                if (mSelectedOptionPosition == 0) {
                    if (binding.submitButton.text == "SUBMIT") {
                        toast.setText("Please select an option")
                        toast.show()
                    } else {
                        mCurrentPosition++

                        when {
                            mCurrentPosition <= mQuestions!!.size -> {
                                setQuestions()
                            }
                            else -> {
                                toast.setText("You have successfully completed the Quiz")
                                toast.show()
                            }

                        }
                    }
                } else {
                    val question = mQuestions?.get(mCurrentPosition - 1)
                    if (question!!.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                        Log.d("checkAnswer: ", "I am the wrong answer")
                        configDialog("error", question.information)

                    } else {
                        configDialog("success", question.information)
                    }
                    answerView(question!!.answer, R.drawable.correct_option_border_bg)
                    Log.d("mSelectedOptionPosition: ", mSelectedOptionPosition.toString())
                    if (mCurrentPosition == mQuestions!!.size) {
                        binding.submitButton.text = "FINISH"
                    } else {
                        binding.submitButton.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0

                    // when the question is answered, set the current questions is not clickable until go to next one
                    setOptionClickable(false)
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.opt1.option.background =
                ContextCompat.getDrawable(requireActivity(), drawableView)
            2 -> binding.opt2.option.background =
                ContextCompat.getDrawable(requireActivity(), drawableView)
            3 -> binding.opt3.option.background =
                ContextCompat.getDrawable(requireActivity(), drawableView)
            4 -> binding.opt4.option.background =
                ContextCompat.getDrawable(requireActivity(), drawableView)
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOption: Int) {
        configDefaultOptionsView()
        mSelectedOptionPosition = selectedOption

        textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark))
        var typeface: Typeface? =
            ResourcesCompat.getFont(requireActivity(), R.font.notosansjp_black)
        textView.typeface = typeface
        textView.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.selected_option_border_bg
        )
    }

    // set all options isClickable
    private fun setOptionClickable(isClickable: Boolean) {
        binding.opt1.option.isClickable = isClickable
        binding.opt2.option.isClickable = isClickable
        binding.opt3.option.isClickable = isClickable
        binding.opt4.option.isClickable = isClickable

    }

    private fun configDialog(type: String, info: String) {
        MaterialDialog(requireContext()).show {
            title(text = type)
            message(text = info)
        }
    }
}

/*
    Referred from:
    https://www.youtube.com/watch?v=b21fiIyOW4A&t=203s&ab_channel=tutorialsEU
 */