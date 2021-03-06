package com.example.safodel.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

import android.content.Context

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.databinding.QuizHistoryRvBinding
import com.example.safodel.entity.TimeEntryWithQuizResult
import com.example.safodel.util.DateStringConverter
import com.example.safodel.viewModel.HistoryDetailViewModel


class QuizHistoryAdapter(results: MutableList<TimeEntryWithQuizResult>, context: Context) :
    RecyclerView.Adapter<QuizHistoryAdapter.ViewHolder?>() {
    private var quizResults = results
    private val myContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: QuizHistoryRvBinding =
            QuizHistoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val record = quizResults[position]

        viewHolder.binding.tvRvDate.text =
            DateStringConverter().parseDateToStr("dd-MM-yyyy  hh:mm  aa", record.timeEntry.time).uppercase()


        val model: HistoryDetailViewModel = ViewModelProvider(myContext as FragmentActivity).get(
            HistoryDetailViewModel::class.java
        )

        // set the live data for the quiz result history user selected
        viewHolder.binding.ivDetailButton.setOnClickListener {
            model.setResult(record)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = quizResults.size

    class ViewHolder(val binding: QuizHistoryRvBinding) : RecyclerView.ViewHolder(binding.root)
}