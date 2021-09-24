package com.example.safodel.fragment.menuBottom.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.safodel.databinding.FragmentQuizHistoryBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.HistoryDetailViewModel
import com.example.safodel.viewModel.QuizResultViewModel
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.adapter.QuizHistoryAdapter
import com.example.safodel.entity.QuizResult


class QuizHistoryFragment : BasicFragment<FragmentQuizHistoryBinding>(FragmentQuizHistoryBinding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var historyViewModel: HistoryDetailViewModel
    private lateinit var quizResultViewModel: QuizResultViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizHistoryBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarGray(toolbar)

        mainActivity = activity as MainActivity

        historyViewModel = mainActivity.getHistoryDetailViewModel()
        quizResultViewModel = mainActivity.getQuizResultViewModel()

        quizResultViewModel.allQuizResults.observe(requireActivity(), Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    val adapter = QuizHistoryAdapter(it as MutableList<QuizResult>, requireActivity())
                    binding.recyclerView.addItemDecoration(
                        DividerItemDecoration(
                            activity, LinearLayoutManager.VERTICAL
                        )
                    )
                    binding.recyclerView.adapter = adapter
                    layoutManager = LinearLayoutManager(activity)
                    binding.recyclerView.layoutManager = layoutManager
                }
            } else {
                Toast.makeText(activity, "Quiz Result Did Not Exist", Toast.LENGTH_SHORT).show()
            }
        })

        historyViewModel.getResult().observe(viewLifecycleOwner,{
            var detail = ""
            if (it != null) {
                detail += "Time: ${it.timeEntry}\n" +
                        "Question: ${it.question_heading}\n" +
                        "Information: ${it.question_info}\n" +
                        "User's answer is correct: ${it.isCorrect}"
            }
            binding.historyDetail.detail.text = detail
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}