package com.example.safodel.fragment.menuBottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentQuizBinding
import com.example.safodel.fragment.BasicFragment


class QuizFragment : BasicFragment<FragmentQuizBinding>(FragmentQuizBinding::inflate) {
    private lateinit var toast: Toast
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        toast = Toast.makeText(requireActivity(),null,Toast.LENGTH_SHORT)

        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        configBtnOnClickListener()

        binding.historyBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty_12,0,
            R.drawable.ic_baseline_navigate_next_24,0)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configBtnOnClickListener() {
        binding.startBtn.button.setOnClickListener {
            findNavController().navigate(R.id.exam1Fragment, null, navAnimationLeftToRight())
        }

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(R.id.quizHistoryFragment, null, navAnimationLeftToRight())
        }
    }
}