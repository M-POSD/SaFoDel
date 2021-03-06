package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.databinding.FragmentWelcomeSafodelBinding

class SafodelViewAdapter(val context: Context) :
    RecyclerView.Adapter<SafodelViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentWelcomeSafodelBinding =
            FragmentWelcomeSafodelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // view is visible when it is selected
        when (position) {
            0 -> {
                viewHolder.binding.welcomeInformation2.welcomeInformation2Layout.visibility =
                    View.GONE
            }
            1 -> viewHolder.binding.welcomeInformation1.welcomeInformation1Layout.visibility =
                View.GONE
        }
    }

    override fun getItemCount() = 2

    class ViewHolder(val binding: FragmentWelcomeSafodelBinding) :
        RecyclerView.ViewHolder(binding.root)
}