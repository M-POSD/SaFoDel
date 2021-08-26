package com.example.safodel.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardV1Binding
import com.example.safodel.model.Gear

class Gear4Adapter(val contxt: Context, gears: MutableList<Gear>) :
    RecyclerView.Adapter<Gear4Adapter.ViewHolder>() {
    private var gear: MutableList<Gear> = gears

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardV1Binding =
            DetailCardV1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Gear = gear[position]
        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.title.text = info.title
        viewHolder.binding.subtitle.text =
            HtmlCompat.fromHtml(info.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        viewHolder.binding.statistics.text = info.statistics
    }

    override fun getItemCount(): Int {
        return gear.size
    }

    class ViewHolder(binding: DetailCardV1Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardV1Binding = binding
    }
}