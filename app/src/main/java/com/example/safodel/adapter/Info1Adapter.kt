package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardBinding
import com.example.safodel.model.Info1

class Info1Adapter(val contxt: Context, infos: MutableList<Info1>) :
    RecyclerView.Adapter<Info1Adapter.ViewHolder>() {
    private var info1: MutableList<Info1> = infos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardBinding = DetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Info1 = info1[position]

        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.title.text =  "Wear helmet"

        viewHolder.binding.subtitle.text = info.description
    }

    override fun getItemCount(): Int {
        return info1.size
    }

    class ViewHolder(binding: DetailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardBinding = binding
    }
}