package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardBinding
import com.example.safodel.model.Info

class InfoAdapter(val contxt: Context, infos: MutableList<Info>) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    private var info: MutableList<Info> = infos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DetailCardBinding = DetailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: Info = info[position]

        viewHolder.binding.title.text =  info.title

        viewHolder.binding.subtitle.text = info.description

        viewHolder.binding.statistics.text = info.statistics
    }

    override fun getItemCount(): Int {
        return info.size
    }

    class ViewHolder(binding: DetailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardBinding = binding
    }
}