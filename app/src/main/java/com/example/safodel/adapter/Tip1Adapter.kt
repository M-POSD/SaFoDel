package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.LargeCardBinding
import com.example.safodel.model.Tip1Info

class Tip1Adapter(val contxt: Context, tips: MutableList<Tip1Info>) :
    RecyclerView.Adapter<Tip1Adapter.ViewHolder>() {
    private var tip1Info: MutableList<Tip1Info> = tips

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LargeCardBinding = LargeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Tip1Info = tip1Info[position]

        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.text.text = (info.description)
    }

    override fun getItemCount(): Int {
        return tip1Info.size
    }

    class ViewHolder(binding: LargeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: LargeCardBinding = binding
    }
}