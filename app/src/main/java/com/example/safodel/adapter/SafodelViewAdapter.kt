package com.example.safodel.adapter

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.os.Build
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (position) {
            0 -> {
//                val safodel = viewHolder.binding.welcomeInformation1.safodel
//                val paint: Paint = safodel.paint
//                val bound = Rect()
//                paint.getTextBounds(safodel.text, 0, safodel.text.length, bound)
//
//                val shader = LinearGradient(0f, 0f,
//                    bound.width().toFloat(),
//                    bound.height().toFloat(),
//                    ContextCompat.getColor(context, R.color.primary_green),
//                    ContextCompat.getColor(context, R.color.wrong_border_color),
//                    Shader.TileMode.CLAMP)
//                viewHolder.binding.welcomeInformation1.safodel.paint.shader = shader

                viewHolder.binding.welcomeInformation2.welcomeInformation2Layout.visibility =
                    View.GONE
            }
            1 -> viewHolder.binding.welcomeInformation1.welcomeInformation1Layout.visibility =
                View.GONE
        }
    }

    override fun getItemCount() = 2

    class ViewHolder(binding: FragmentWelcomeSafodelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: FragmentWelcomeSafodelBinding = binding
    }
}