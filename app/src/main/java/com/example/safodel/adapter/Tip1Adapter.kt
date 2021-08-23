package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardBinding
import com.example.safodel.model.Tip1

class Tip1Adapter(val contxt: Context, tips: MutableList<Tip1>) :
    RecyclerView.Adapter<Tip1Adapter.ViewHolder>() {
    private var tip1: MutableList<Tip1> = tips

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardBinding = DetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Tip1 = tip1[position]

        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.title.text =  "No cellphone"
        viewHolder.binding.subtitle.text = info.description
    }

    override fun getItemCount(): Int {
        return tip1.size
    }

    class ViewHolder(binding: DetailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardBinding = binding
    }
}