package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardV0Binding
import com.example.safodel.model.Gear

class GearAdapter(val contxt: Context, gears: MutableList<Gear>) :
    RecyclerView.Adapter<GearAdapter.ViewHolder>() {
    private var gear: MutableList<Gear> = gears

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardV0Binding =
            DetailCardV0Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Gear = gear[position]

        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.title.text = info.title
        viewHolder.binding.subtitle.text = info.description
        viewHolder.binding.statistics.text = info.statistics
    }

    override fun getItemCount(): Int {
        return gear.size
    }

    class ViewHolder(binding: DetailCardV0Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardV0Binding = binding
    }
}