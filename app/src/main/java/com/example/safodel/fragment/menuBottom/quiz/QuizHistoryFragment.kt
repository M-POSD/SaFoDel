package com.example.safodel.fragment.menuBottom.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.databinding.FragmentQuizHistoryBinding
import com.example.safodel.fragment.BasicFragment
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
import timber.log.Timber


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


        /*
            Observing all quiz results store in the local database
            Set these data to recycle view
            If no data in the database, set the view gone
         */
        activity?.let { fragmentActivity ->
            timeEntryWithQuizResultViewModel.allQuizResults.observe(fragmentActivity, Observer {
                if (!it.isNullOrEmpty()) {
                    val adapter = QuizHistoryAdapter(
                        it as MutableList<TimeEntryWithQuizResult>,
                        fragmentActivity
                    )
                    try {
                        recyclerView.addItemDecoration(
                            DividerItemDecoration(
                                activity, LinearLayoutManager.VERTICAL
                            )
                        )
                    }catch (e:Exception){
                        Timber.d(e.message)
                    }

                    recyclerView.adapter = adapter
                    layoutManager = LinearLayoutManager(activity)
                    recyclerView.layoutManager = layoutManager
                } else {
                    binding.historyDetail.timeDetail.text = getString(R.string.none)
                    binding.historyDetail.detail.visibility = View.GONE
                }
            })
        }

        // history view model for observing the one user selected
        historyViewModel.getResult().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.historyDetail.detail.visibility = View.VISIBLE
                binding.historyDetail.historyDetailScrollView.fullScroll(ScrollView.FOCUS_UP)
                val result = it.quizResults
                binding.historyDetail.timeDetail.text =
                    DateStringConverter().parseDateToStr("dd-MM-yyyy hh:mm:ss aa", it.timeEntry.time)

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
//        historyViewModel.setResult(null)
        _binding = null
    }

    /**
     * display the all details for the specific quiz result
     */
    @SuppressLint("SetTextI18n")
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
                binding.historyDetail.q1.qHeading.text = question
                binding.historyDetail.q1.qDesc.text = questionHeading
                binding.historyDetail.q1.infoHeading.text = info
                binding.historyDetail.q1.infoDesc.text = questionInfo
                binding.historyDetail.q1.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            2-> {
                binding.historyDetail.q2.qHeading.text = question
                binding.historyDetail.q2.qDesc.text = questionHeading
                binding.historyDetail.q2.infoHeading.text = info
                binding.historyDetail.q2.infoDesc.text = questionInfo
                binding.historyDetail.q2.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            3-> {
                binding.historyDetail.q3.qHeading.text = question
                binding.historyDetail.q3.qDesc.text = questionHeading
                binding.historyDetail.q3.infoHeading.text = info
                binding.historyDetail.q3.infoDesc.text = questionInfo
                binding.historyDetail.q3.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            4-> {
                binding.historyDetail.q4.qHeading.text = question
                binding.historyDetail.q4.qDesc.text = questionHeading
                binding.historyDetail.q4.infoHeading.text = info
                binding.historyDetail.q4.infoDesc.text = questionInfo
                binding.historyDetail.q4.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
            5-> {
                binding.historyDetail.q5.qHeading.text = question
                binding.historyDetail.q5.qDesc.text = questionHeading
                binding.historyDetail.q5.infoHeading.text = info
                binding.historyDetail.q5.infoDesc.text = questionInfo
                binding.historyDetail.q5.isCorrectHeading.text = "$correctAnswer  $isCorrect"
            }
        }
    }
}