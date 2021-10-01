package com.example.safodel.fragment.menuBottom.quiz

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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.example.safodel.R
import com.example.safodel.databinding.FragmentQuizPageBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Question
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntry
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.TimeEntryWithQuizResultViewModel
import java.util.*
import kotlin.collections.ArrayList


class QuizPageFragment : BasicFragment<FragmentQuizPageBinding>(FragmentQuizPageBinding::inflate),
    View.OnClickListener {
    private lateinit var mQuestions: MutableList<Question>
    private var mCurrentPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var totalScore = 0
    private lateinit var toast: Toast
    private lateinit var results: MutableList<QuizResult>
    private lateinit var timeEntryWithQuizResultViewModel: TimeEntryWithQuizResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizPageBinding.inflate(inflater, container, false)
        toast = Toast.makeText(activity, null, Toast.LENGTH_SHORT)
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        mQuestions = Question.init()
        results = ArrayList()

        configOnClickListener()
        setQuestions()

        timeEntryWithQuizResultViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(TimeEntryWithQuizResultViewModel::class. java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * set questions to the view
     */
    private fun setQuestions() {
        // default image view
        binding.image.visibility = View.GONE

        binding.submitBtn.button.text = getString(R.string.submit_button)
        // when the question haven't answered, set the current questions is clickable until has been answer
        setOptionClickable(true)
        val question = mQuestions!![mCurrentPosition - 1]

        // set info view isGone
        infoView(0)


        binding.progressBar.progress = mCurrentPosition
        binding.progress.text = "$mCurrentPosition/${binding.progressBar.max}"


        binding.question.text = getString(question!!.question)

        if (question.image != 0) {
            binding.image.setImageResource(question.image)
            binding.image.visibility = View.VISIBLE
        }
        configDefaultOptionsView()
        binding.questionInfo.text = getString(question.information)

    }

    /**
     * setup the default option view in each question
     */
    private fun configDefaultOptionsView() {
        val options = ArrayList<TextView>()
        val question = mQuestions!![mCurrentPosition - 1]
        options.add(0, binding.opt1.option)
        binding.opt1.option.text = getString(question.option1)
        options.add(1, binding.opt2.option)
        binding.opt2.option.text = getString(question.option2)

        // if the question does not have the third option, view gone
        if (question.option3 == 0) {
            binding.opt3.option.visibility = View.GONE
        } else {
            options.add(2, binding.opt3.option)
            binding.opt3.option.text = getString(question.option3)
            binding.opt3.option.visibility = View.VISIBLE
        }

        // if the question does not have the fourth option, view gone
        if (question.option4 == 0) {
            binding.opt4.option.visibility = View.GONE
        } else {
            options.add(2, binding.opt4.option)
            binding.opt4.option.text = getString(question.option4)
            binding.opt4.option.visibility = View.VISIBLE
        }

        // set the default view for each option
        for (option in options) {
            option.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.default_option_icon,0)
            option.setTextColor(ContextCompat.getColor(requireActivity(), R.color.bottom_nav_gray))
            var typeface: Typeface? =
                ResourcesCompat.getFont(requireActivity(), R.font.opensans_bold)
            option.typeface = typeface
            option.background = ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * config all OnClickListener this fragment
     */
    private fun configOnClickListener() {
        binding.opt1.option.setOnClickListener(this)
        binding.opt2.option.setOnClickListener(this)
        binding.opt3.option.setOnClickListener(this)
        binding.opt4.option.setOnClickListener(this)
        binding.submitBtn.button.setOnClickListener(this)
    }

    /**
     * override the onClick listener
     */
    override fun onClick(v: View?) {
        when (v) {
            binding.opt1.option -> selectedOptionView(binding.opt1.option, 1)
            binding.opt2.option -> selectedOptionView(binding.opt2.option, 2)
            binding.opt3.option -> selectedOptionView(binding.opt3.option, 3)
            binding.opt4.option -> selectedOptionView(binding.opt4.option, 4)

            binding.submitBtn.button -> {
                /*
                 the option have not been selected || need to go to the next page
                 || need to go to other frag
                 */
                if (mSelectedOptionPosition == 0) {

                    // no option has been selected, give a warning message
                    if (binding.submitBtn.button.text == "SUBMIT") {
                        toast.cancel()
                        toast.setText(getString(R.string.notify_select_option))
                        toast.show()
                    } else {
                        mCurrentPosition++

                        when {
                            mCurrentPosition <= mQuestions!!.size -> {
                                setQuestions()
                            }
                            else -> {
                                var timeEntry = TimeEntry(Calendar.getInstance().time)

                                // store quiz results to database
                                timeEntryWithQuizResultViewModel.addTimeEntryWithQuizResults(timeEntry, results)

                                var arg = bundleOf(
                                    Pair("score", totalScore),
                                    Pair("numOfQuestions", mQuestions.size)
                                )
                                findNavController().navigate(R.id.quizResultFragment, arg, navAnimationLeftToRight())
                            }

                        }
                    }
                } else { // user has selected the option and just clicked the submit
                    val question = mQuestions?.get(mCurrentPosition - 1)
                    if (question!!.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg, R.color.wrong_border_color, R.drawable.error_icon)
                        results.add(QuizResult(0, question.question, question.information, false))
                    } else {
                        results.add(QuizResult(1, question.question, question.information, true))
                    }

                    answerView(question!!.answer, R.drawable.correct_option_border_bg, R.color.correct_border_color, R.drawable.success_icon)
                    infoView(if (question!!.answer == mSelectedOptionPosition) 1 else 2)

                    if (mCurrentPosition == mQuestions!!.size) {
                        binding.submitBtn.button.text = getString(R.string.finish_button)
                    } else {
                        binding.submitBtn.button.text = getString(R.string.go_next_button)
                    }
                    mSelectedOptionPosition = 0

                    // when the question is answered, set the current questions is not clickable until go to next one
                    setOptionClickable(false)
                }
            }
        }
    }

    /**
     * display answer view (correct or incorrect) with the border, background and icon received
     */
    private fun answerView(answer: Int, drawableView: Int, color: Int,icon: Int) {
        when (answer) {
            1 -> {
                binding.opt1.option.background =
                    ContextCompat.getDrawable(requireActivity(), drawableView)
                binding.opt1.option.setTextColor(ContextCompat.getColor(requireActivity(), color))
                binding.opt1.option.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0)
            }
            2 -> {
                binding.opt2.option.background =
                    ContextCompat.getDrawable(requireActivity(), drawableView)
                binding.opt2.option.setTextColor(ContextCompat.getColor(requireActivity(), color))
                binding.opt2.option.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0)
            }
            3 -> {
                binding.opt3.option.background =
                    ContextCompat.getDrawable(requireActivity(), drawableView)
                binding.opt3.option.setTextColor(ContextCompat.getColor(requireActivity(), color))
                binding.opt3.option.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0)
            }
            4 -> {
                binding.opt4.option.background =
                    ContextCompat.getDrawable(requireActivity(), drawableView)
                binding.opt4.option.setTextColor(ContextCompat.getColor(requireActivity(), color))
                binding.opt4.option.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0)
            }
        }
    }

    /**
     * display the information of the question with different border
     */
    private fun infoView(infoPosition: Int) {
        when (infoPosition) {
            0 -> {
                binding.questionInfo.visibility = View.GONE
            }
            // correct answer info
            1 -> {
                binding.questionInfo.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.correct_info_border_bg)
                binding.questionInfo.visibility = View.VISIBLE
                totalScore++
            }
            // wrong answer info
            2 -> {
                binding.questionInfo.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.wrong_info_border_bg)
                binding.questionInfo.visibility = View.VISIBLE
            }

        }
    }

    /**
     * set the selected option view in different style
     */
    private fun selectedOptionView(textView: TextView, selectedOption: Int) {
        configDefaultOptionsView()
        mSelectedOptionPosition = selectedOption

        textView.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.correct_border_color
            )
        )

        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.success_icon,0)

        var typeface: Typeface? =
            ResourcesCompat.getFont(requireActivity(), R.font.opensans_bold)
        textView.typeface = typeface
        textView.background = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * set all options isClickable
     */
    private fun setOptionClickable(isClickable: Boolean) {
        binding.opt1.option.isClickable = isClickable
        binding.opt2.option.isClickable = isClickable
        binding.opt3.option.isClickable = isClickable
        binding.opt4.option.isClickable = isClickable
    }
}

/*
    Referred from:
    https://www.youtube.com/watch?v=b21fiIyOW4A&t=203s&ab_channel=tutorialsEU
 */