package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.AnalysisGenericInfoBinding
import com.example.safodel.model.AnalysisGenericInfo

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

    class ViewHolder(val binding: AnalysisGenericInfoBinding) : RecyclerView.ViewHolder(binding.root)
}