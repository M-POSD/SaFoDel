package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.FragmentEpicStyle3Binding
import com.example.safodel.model.GroupCard2Data

class EpicStyle3Adapter(val context: Context, group2Data: MutableList<GroupCard2Data>):
    RecyclerView.Adapter<EpicStyle3Adapter.ViewHolder>() {
    private val group2: MutableList<GroupCard2Data> = group2Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentEpicStyle3Binding =
            FragmentEpicStyle3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data: GroupCard2Data = group2[position]

        viewHolder.binding.roadSignImage.setImageResource(data.image)
        viewHolder.binding.roadSignTitle.text = context.getString(data.title_id)
        viewHolder.binding.roadSignDescription.text = context.getString(data.description_id)

    }

    override fun getItemCount() = group2.size

    class ViewHolder(binding: FragmentEpicStyle3Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: FragmentEpicStyle3Binding = binding
    }
}