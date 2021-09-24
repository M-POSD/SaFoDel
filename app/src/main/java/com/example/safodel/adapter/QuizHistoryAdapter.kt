package com.example.safodel.adapter

import androidx.recyclerview.widget.RecyclerView

import android.R
import android.content.Context

import androidx.fragment.app.FragmentActivity

import androidx.lifecycle.ViewModelProvider

import androidx.annotation.NonNull

import android.view.LayoutInflater

import android.view.ViewGroup
import com.example.safodel.databinding.FragmentQuizHistoryBinding
import com.example.safodel.databinding.FragmentWelcomeSafodelBinding
import com.example.safodel.databinding.QuizHistoryRvBinding
import com.example.safodel.entity.QuizResult
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.HistoryDetailViewModel


class QuizHistoryAdapter(results: MutableList<QuizResult>, context: Context) :
    RecyclerView.Adapter<QuizHistoryAdapter.ViewHolder?>() {
    private var quizResults = results
    private val myContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: QuizHistoryRvBinding =
            QuizHistoryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val record = quizResults[position]
        viewHolder.binding.tvRvDate.text = record.timeEntry.toString()
//        viewHolder.binding.ivItemDelete.setOnClickListener {
//            quizResults.remove(record)
//            notifyDataSetChanged()
//        }
        val model: HistoryDetailViewModel = (myContext as MainActivity).getHistoryDetailViewModel()

        viewHolder.binding.ivDetailButton.setOnClickListener {
            model.setResult(record)
        }
    }

    override fun getItemCount(): Int = quizResults.size

    class ViewHolder(binding: QuizHistoryRvBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: QuizHistoryRvBinding = binding
    }
}