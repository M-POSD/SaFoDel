package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardBinding
import com.example.safodel.model.Tip

class TipAdapter(val contxt: Context, tips: MutableList<Tip>) :
    RecyclerView.Adapter<TipAdapter.ViewHolder>() {
    private var tip: MutableList<Tip> = tips

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // binding the view of detail_card
        val binding: DetailCardBinding =
            DetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Tip = tip[position]
        viewHolder.binding.title.text = info.title
        viewHolder.binding.subtitle.text = info.description
        viewHolder.binding.statistics.text = info.statistics
    }

    override fun getItemCount(): Int {
        return tip.size
    }

    class ViewHolder(binding: DetailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardBinding = binding
    }
}