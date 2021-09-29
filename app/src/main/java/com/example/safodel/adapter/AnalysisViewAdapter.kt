package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.AnalysisGenericInfoBinding
import com.example.safodel.databinding.FragmentHeroFeatureBinding
import com.example.safodel.model.AnalysisGenericInfo
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.WeatherViewModel
import java.util.*

class AnalysisViewAdapter(val context: Context):
    RecyclerView.Adapter<AnalysisViewAdapter.ViewHolder>() {
    private var genericInfoList = AnalysisGenericInfo.init()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AnalysisGenericInfoBinding =
            AnalysisGenericInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val genericInfo = genericInfoList[position]
        viewHolder.binding.analysisHeading.text = context.getString(genericInfo.heading_id)
        viewHolder.binding.analysisDescription.text = context.getString(genericInfo.description_id)
    }

    override fun getItemCount() = 5

    class ViewHolder(binding: AnalysisGenericInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: AnalysisGenericInfoBinding = binding
    }
}