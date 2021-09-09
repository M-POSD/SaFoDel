package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.GroupCardV2Binding
import com.example.safodel.model.*

class GroupCard2Adapter(val context: Context, group2Data: MutableList<GroupCard2Data>) :
    RecyclerView.Adapter<GroupCard2Adapter.ViewHolder>() {
    private var group2: MutableList<GroupCard2Data> = group2Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // binding the view of group_card_v2
        val binding: GroupCardV2Binding =
            GroupCardV2Binding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var data: GroupCard2Data = group2[position]
        viewHolder.binding.imageLeft.statistics.visibility = View.VISIBLE
        viewHolder.binding.imageRight.linearLayout2.visibility = View.VISIBLE
        viewHolder.binding.pureText.linearLayout3.visibility = View.VISIBLE
        viewHolder.binding.imageLeft.linearLayout1.visibility = View.VISIBLE
        viewHolder.binding.imageRight.statistics.visibility = View.VISIBLE

        // if card type == 1, put the image on left, otherwise put it on right
        when (data.cardType) {
            1 -> {
                viewHolder.binding.imageLeft.title.text = data.title
                if (data.statistics.isEmpty()) {
                    viewHolder.binding.imageLeft.statistics.visibility = View.GONE
                }
                viewHolder.binding.imageLeft.statistics.text = data.statistics
                viewHolder.binding.imageLeft.description.text = data.description
                viewHolder.binding.imageLeft.image.setImageResource(data.image)
                viewHolder.binding.imageRight.linearLayout2.visibility = View.GONE
                viewHolder.binding.pureText.linearLayout3.visibility = View.GONE
            }
            4 -> {
                viewHolder.binding.pureText.description.text = data.description
                if (data.title.isNotEmpty()) {
                    viewHolder.binding.pureText.description2.text = data.title
                    viewHolder.binding.pureText.description2.visibility = View.VISIBLE
                }
                viewHolder.binding.imageLeft.linearLayout1.visibility = View.GONE
                viewHolder.binding.imageRight.linearLayout2.visibility = View.GONE
            }
            else -> {
                viewHolder.binding.imageRight.title.text = data.title
                if (data.statistics.isEmpty()) {
                    viewHolder.binding.imageRight.statistics.visibility = View.GONE
                }
                viewHolder.binding.imageRight.description.text = data.description
                viewHolder.binding.imageRight.statistics.text = data.statistics
                viewHolder.binding.imageRight.image.setImageResource(data.image)
                viewHolder.binding.imageLeft.linearLayout1.visibility = View.GONE
                viewHolder.binding.pureText.linearLayout3.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return group2.size
    }

    class ViewHolder(binding: GroupCardV2Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: GroupCardV2Binding = binding
    }
}