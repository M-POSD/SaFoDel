package com.example.safodel.fragment.menuL.quiz

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
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.example.safodel.R
import com.example.safodel.databinding.FragmentQuizPageBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.model.Question


class QuizPageFragment : BasicFragment<FragmentQuizPageBinding>(FragmentQuizPageBinding::inflate),
    View.OnClickListener {
    private lateinit var mQuestions: MutableList<Question>
    private var mCurrentPosition: Int = 1
    private var mSelectedOptionPosition: Int = 0
    private var totalScore = 0
    private lateinit var toast: Toast

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

        configOnClickListener()
        setQuestions()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setQuestions() {
        // default image view
        binding.image.visibility = View.GONE

        binding.submitBtn.button.text = "SUBMIT"
        // when the question haven't answered, set the current questions is clickable until has been answer
        setOptionClickable(true)
        val question = mQuestions!![mCurrentPosition - 1]

        // set info view isGone
        infoView(0, question)


        binding.progressBar.progress = mCurrentPosition
        binding.progress.text = "$mCurrentPosition/${binding.progressBar.max}"

        binding.question.text = question!!.question
        if (question.image != 0) {
            binding.image.setImageResource(question.image)
            binding.image.visibility = View.VISIBLE
        }
        configDefaultOptionsView()
        binding.questionInfo.text = question.information

    }

    private fun configDefaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.opt1.option)
        options.add(1, binding.opt2.option)

        val question = mQuestions!![mCurrentPosition - 1]
        if (question.option3 == "") {
            binding.opt3.option.visibility = View.GONE
        } else {
            options.add(2, binding.opt3.option)
            binding.opt3.option.visibility = View.VISIBLE
        }
        if (question.option4 == "") {
            binding.opt4.option.visibility = View.GONE
        } else {
            options.add(2, binding.opt4.option)
            binding.opt4.option.visibility = View.VISIBLE
        }

        binding.opt1.option.text = question.option1
        binding.opt2.option.text = question.option2
        binding.opt3.option.text = question.option3
        binding.opt4.option.text = question.option4

        for (option in options) {
            option.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.default_option_icon,0)
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
        binding.submitBtn.button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("onClick", v.toString())
        when (v) {
            binding.opt1.option -> selectedOptionView(binding.opt1.option, 1)
            binding.opt2.option -> selectedOptionView(binding.opt2.option, 2)
            binding.opt3.option -> selectedOptionView(binding.opt3.option, 3)
            binding.opt4.option -> selectedOptionView(binding.opt4.option, 4)
            binding.submitBtn.button -> {
                if (mSelectedOptionPosition == 0) {
                    if (binding.submitBtn.button.text == "SUBMIT") {
                        toast.setText("Please select an option")
                        toast.show()
                    } else {
                        mCurrentPosition++

                        when {
                            mCurrentPosition <= mQuestions!!.size -> {
                                setQuestions()
                            }
                            else -> {
//                                configDialog("Success", "You have successfully completed the Quiz")
//                                binding.submitBtn.button.text = "RETURN"

                                // allow user go back
                                mCurrentPosition--
//                                findNavController().popBackStack(R.id.examFinishFragment, true)
                                var arg = bundleOf(
                                    Pair("score", totalScore),
                                    Pair("numOfQuestions", mQuestions.size)
                                )
                                findNavController().navigate(R.id.examFinishFragment, arg, navAnimationLeftToRight())
                            }

                        }
                    }
                } else {
                    val question = mQuestions?.get(mCurrentPosition - 1)
                    if (question!!.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg, R.color.wrong_border_color, R.drawable.error_icon)
                    }
                    answerView(question!!.answer, R.drawable.correct_option_border_bg, R.color.correct_border_color, R.drawable.success_icon)
                    infoView(if (question!!.answer == mSelectedOptionPosition) 5 else 6, question)

                    if (mCurrentPosition == mQuestions!!.size) {
                        binding.submitBtn.button.text = "FINISH"
                    } else {
                        binding.submitBtn.button.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0

                    // when the question is answered, set the current questions is not clickable until go to next one
                    setOptionClickable(false)
                }
            }
        }
    }

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

    private fun infoView(infoPosition: Int, question: Question) {
        when (infoPosition) {
            0 -> {
                binding.opt1.info.visibility = View.GONE
                binding.opt2.info.visibility = View.GONE
                binding.opt3.info.visibility = View.GONE
                binding.opt4.info.visibility = View.GONE
                binding.questionInfo.visibility = View.GONE

            }
            1 -> {
                binding.opt1.info.text = question.information
                binding.opt1.info.visibility = View.VISIBLE
            }
            2 -> {
                binding.opt2.info.text = question.information
                binding.opt2.info.visibility = View.VISIBLE
            }
            3 -> {
                binding.opt3.info.text = question.information
                binding.opt3.info.visibility = View.VISIBLE
            }
            4 -> {
                binding.opt4.info.text = question.information
                binding.opt4.info.visibility = View.VISIBLE
            }
            // correct answer info
            5 -> {
                binding.questionInfo.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.correct_info_border_bg)
                binding.questionInfo.visibility = View.VISIBLE
                totalScore++
            }
            // wrong answer info
            6 -> {
                binding.questionInfo.background =
                    ContextCompat.getDrawable(requireActivity(), R.drawable.wrong_info_border_bg)
                binding.questionInfo.visibility = View.VISIBLE
            }

        }
    }

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
            ResourcesCompat.getFont(requireActivity(), R.font.notosansjp_bold)
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