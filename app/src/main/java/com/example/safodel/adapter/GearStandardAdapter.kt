package com.example.safodel.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.DetailCardStandardBinding
import com.example.safodel.databinding.DetailCardV4Binding
import com.example.safodel.model.GearStandard

class GearStandardAdapter(val context: Context, gearStandards: MutableList<GearStandard>) :
    RecyclerView.Adapter<GearStandardAdapter.ViewHolder>() {
    private var gearStandards: MutableList<GearStandard> = gearStandards

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // binding the view of detail_card_v4
        val binding: DetailCardStandardBinding =
            DetailCardStandardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val info: GearStandard = gearStandards[position]
        viewHolder.binding.image.setImageResource(info.image)
        viewHolder.binding.title.text = info.title
    }

    override fun getItemCount(): Int {
        return gearStandards.size
    }

    class ViewHolder(binding: DetailCardStandardBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: DetailCardStandardBinding = binding
    }
}