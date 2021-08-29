package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.GroupCardV2Binding
import com.example.safodel.model.*

class GroupCard2Adapter(val context: Context, group2Data: MutableList<GroupCard2Data>) :
    RecyclerView.Adapter<GroupCard2Adapter.ViewHolder>() {
    private var group2: MutableList<GroupCard2Data> = group2Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // binding the view of detail_card
        val binding: GroupCardV2Binding =
            GroupCardV2Binding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var data: GroupCard2Data = group2[position]

        // if card type == 1, put the image on left, otherwise put it on right
        if (data.cardType == 1) {
            viewHolder.binding.imageLeft.title.text = data.title
            viewHolder.binding.imageLeft.statistics.text = data.statistics
            viewHolder.binding.imageLeft.description.text = data.description
            viewHolder.binding.imageLeft.image.setImageResource(data.image)
        } else {
            viewHolder.binding.imageRight.title.text = data.title
            viewHolder.binding.imageRight.statistics.text = data.statistics
            viewHolder.binding.imageRight.description.text = data.description
            viewHolder.binding.imageRight.image.setImageResource(data.image)
        }

    }

    override fun getItemCount(): Int {
        return group2.size
    }

    class ViewHolder(binding: GroupCardV2Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: GroupCardV2Binding = binding
    }
}