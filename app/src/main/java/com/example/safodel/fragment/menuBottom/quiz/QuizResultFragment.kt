package com.example.safodel.fragment.menuBottom.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentQuizResaultBinding
import com.example.safodel.fragment.BasicFragment


class QuizResultFragment : BasicFragment<FragmentQuizResaultBinding>(FragmentQuizResaultBinding::inflate) {
    private lateinit var toast: Toast
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizResaultBinding.inflate(inflater, container, false)

        toast = Toast.makeText(requireActivity(),null,Toast.LENGTH_SHORT)
        configDefaultView()
        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)
        configBtnOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * set the default view
     */
    @SuppressLint("SetTextI18n")
    private fun configDefaultView() {
        binding.returnButton.button.text = getString(R.string.again_button)

        val args = getArgument()
        val accuracy = args[0].toFloat() / args[1].toFloat()
        binding.level.text = "${args[0]} / ${args[1]}"
        when {
            // got less than two correct answer
            accuracy < 0.6 -> {
                binding.image.setImageResource(R.drawable.failure)
//                binding.title.text = getString(R.string.result_heading_1)
            }

            // got 3 or 4 correct answers
            accuracy < 1f -> {
                binding.image.setImageResource(R.drawable.medal)
//                binding.title.text = getString(R.string.result_heading_2)
            }

            // answers all questions correctly
            accuracy == 1f -> {
                binding.image.setImageResource(R.drawable.champion)
//                binding.title.text = getString(R.string.result_heading_3)
            }
        }

        binding.historyBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty_12,0,
            R.drawable.ic_baseline_navigate_next_24,0)
    }

    /**
     * config all button onClickListener
     */
    private fun configBtnOnClickListener() {
        binding.returnButton.button.setOnClickListener {
            findNavController().navigate(R.id.quizPageFragment, null, navAnimationLeftToRight())
        }

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(R.id.quizHistoryFragment, null, navAnimationLeftToRight())
        }
    }

    /**
     * Get the user's score of the quiz
     */
    private fun getArgument(): IntArray {
        val args = IntArray(2)
        args[0] = (arguments?.get("score") ?: 0) as Int
        args[1] = (arguments?.get("numOfQuestions") ?: 5) as Int

        return args
    }
}