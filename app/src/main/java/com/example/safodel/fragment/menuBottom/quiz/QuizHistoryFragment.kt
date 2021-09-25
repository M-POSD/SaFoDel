package com.example.safodel.fragment.menuBottom.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.databinding.FragmentQuizHistoryBinding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.HistoryDetailViewModel
import com.example.safodel.viewModel.TimeEntryWithQuizResultViewModel
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.adapter.QuizHistoryAdapter
import com.example.safodel.entity.QuizResult
import com.example.safodel.entity.TimeEntryWithQuizResult
import com.example.safodel.util.DateStringConverter


class QuizHistoryFragment :
    BasicFragment<FragmentQuizHistoryBinding>(FragmentQuizHistoryBinding::inflate) {
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var historyViewModel: HistoryDetailViewModel
    private lateinit var timeEntryWithQuizResultViewModel: TimeEntryWithQuizResultViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizHistoryBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)

        historyViewModel = ViewModelProvider(requireActivity()).get(
            HistoryDetailViewModel::class.java
        )

        timeEntryWithQuizResultViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(TimeEntryWithQuizResultViewModel::class. java)

        recyclerView = binding.recyclerView


        timeEntryWithQuizResultViewModel.allQuizResults.observe(requireActivity(), Observer {
            Log.d("all results", it.isNullOrEmpty().toString())
            if (!it.isNullOrEmpty()) {
                val adapter = QuizHistoryAdapter(
                    it as MutableList<TimeEntryWithQuizResult>,
                    requireActivity()
                )
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity, LinearLayoutManager.VERTICAL
                    )
                )
                recyclerView.adapter = adapter
                layoutManager = LinearLayoutManager(activity)
                recyclerView.layoutManager = layoutManager
            } else {
//                Toast.makeText(activity, "Quiz Result Did Not Exist", Toast.LENGTH_SHORT).show()
                binding.historyDetail.timeDetail.text = getString(R.string.none)
                binding.historyDetail.detail.visibility = View.GONE
            }
        })

        historyViewModel.getResult().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.historyDetail.detail.visibility = View.VISIBLE
                val result = it.quizResults
                binding.historyDetail.timeDetail.text =
                    DateStringConverter().parseDateToStr("dd-mm-yyyy hh:mm:ss", it.timeEntry.time)

                var count = 1
                for (i in result) {
                    when (count) {
                        1 -> configQuizResultView(1, i)

                        2 -> configQuizResultView(2, i)

                        3 -> configQuizResultView(3, i)

                        4 -> configQuizResultView(4, i)

                        else -> configQuizResultView(5, i)
                    }
                    count += 1
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configQuizResultView(num: Int, quizResult: QuizResult) {
        val question = getString(R.string.question)
        val questionHeading = getString(quizResult.question_heading)

        val info = getString(R.string.information)
        val questionInfo = getString(quizResult.question_info)

        val correctAnswer = getString(R.string.is_correct)
        val isCorrect =
            if (quizResult.isCorrect) getString(R.string.true_text) else getString(R.string.false_text)

        when(num) {
            1-> {
                binding.historyDetail.q1.qHeading.text = "$question"
                binding.historyDetail.q1.qDesc.text = "$questionHeading"
                binding.historyDetail.q1.infoHeading.text = "$info"
                binding.historyDetail.q1.infoDesc.text = "$questionInfo"
                binding.historyDetail.q1.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            2-> {
                binding.historyDetail.q2.qHeading.text = "$question"
                binding.historyDetail.q2.qDesc.text = "$questionHeading"
                binding.historyDetail.q2.infoHeading.text = "$info"
                binding.historyDetail.q2.infoDesc.text = "$questionInfo"
                binding.historyDetail.q2.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            3-> {
                binding.historyDetail.q3.qHeading.text = "$question"
                binding.historyDetail.q3.qDesc.text = "$questionHeading"
                binding.historyDetail.q3.infoHeading.text = "$info"
                binding.historyDetail.q3.infoDesc.text = "$questionInfo"
                binding.historyDetail.q3.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            4-> {
                binding.historyDetail.q4.qHeading.text = "$question"
                binding.historyDetail.q4.qDesc.text = "$questionHeading"
                binding.historyDetail.q4.infoHeading.text = "$info"
                binding.historyDetail.q4.infoDesc.text = "$questionInfo"
                binding.historyDetail.q4.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            5-> {
                binding.historyDetail.q5.qHeading.text = "$question"
                binding.historyDetail.q5.qDesc.text = "$questionHeading"
                binding.historyDetail.q5.infoHeading.text = "$info"
                binding.historyDetail.q5.infoDesc.text = "$questionInfo"
                binding.historyDetail.q5.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
        }
    }
}