package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardBinding
import com.example.safodel.model.Gear1

class Gear1Adapter(val contxt: Context, gear1s: MutableList<Gear1>) :
    RecyclerView.Adapter<Gear1Adapter.ViewHolder>() {
    private var gear1: MutableList<Gear1> = gear1s

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardBinding = DetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Gear1 = gear1[position]

        viewHolder.binding.title.text =  "Buy latest"

        viewHolder.binding.subtitle.text = info.description
    }

    override fun getItemCount(): Int {
        return gear1.size
    }

    class ViewHolder(binding: DetailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardBinding = binding
    }
}