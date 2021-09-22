package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.FragmentEpicStyle2Binding
import com.example.safodel.model.GroupCard2Data

class EpicStyle2Adapter(val context: Context, group2Data: MutableList<GroupCard2Data>):
    RecyclerView.Adapter<EpicStyle2Adapter.ViewHolder>() {
    private var group2: MutableList<GroupCard2Data> = group2Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentEpicStyle2Binding =
            FragmentEpicStyle2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data: GroupCard2Data = group2[position]
        viewHolder.binding.imageRight.linearLayout2.visibility = View.VISIBLE
        viewHolder.binding.pureText.linearLayout3.visibility = View.VISIBLE
        viewHolder.binding.imageRight.statistics.visibility = View.VISIBLE

        when(data.cardType) {
            4 -> {
                viewHolder.binding.pureText.description.text = context.getString(data.description_id)
                if (data.title_id != 0) {
                    viewHolder.binding.pureText.description2.text = context.getString(data.title_id)
                    viewHolder.binding.pureText.description2.visibility = View.VISIBLE
                }
                viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
            }

            else -> {
                viewHolder.binding.imageRight.title.text = context.getString(data.title_id)
                if (data.statistics_id == 0) {
                    viewHolder.binding.imageRight.statistics.visibility = View.GONE
                } else if (context.getString(data.statistics_id).isEmpty()) {
                    viewHolder.binding.imageRight.statistics.visibility = View.GONE
                }
                else {
                    viewHolder.binding.imageRight.statistics.text = context.getString(data.statistics_id)
                }

                viewHolder.binding.imageRight.description.text = context.getString(data.description_id)
                viewHolder.binding.imageRight.image.setImageResource(data.image)
                viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount() = group2.size

    class ViewHolder(binding: FragmentEpicStyle2Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: FragmentEpicStyle2Binding = binding
    }
}