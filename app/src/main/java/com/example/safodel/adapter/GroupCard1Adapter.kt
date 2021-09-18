package com.example.safodel.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.databinding.GroupCardV1Binding
import com.example.safodel.model.*

class GroupCard1Adapter(val context: Context, group1Data: MutableList<GroupCard1Data>) :
    RecyclerView.Adapter<GroupCard1Adapter.ViewHolder>() {
    private var group1: MutableList<GroupCard1Data> = group1Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // binding the view of group_card_v1
        val binding: GroupCardV1Binding =
            GroupCardV1Binding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var data: GroupCard1Data = group1[position]
        viewHolder.binding.imageRight.linearLayout2.visibility = View.VISIBLE
        viewHolder.binding.pureText.linearLayout3.visibility = View.VISIBLE
        viewHolder.binding.imageLeft.linearLayout1.visibility = View.VISIBLE

        // if card type == 1, put the image on left, otherwise put it on right
        if (data.cardType == 1) {
            viewHolder.binding.imageLeft.description.text = context.getString(data.description_id)
            viewHolder.binding.imageLeft.image.setImageResource(data.image)
            viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
            viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
        } else if (data.cardType == 0) { // present text only
            viewHolder.binding.pureText.description.text = context.getString(data.description_id)
            viewHolder.binding.imageLeft.linearLayout1.visibility = View.INVISIBLE
            viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
        } else if (data.cardType == -1) {
            viewHolder.binding.imageRight.description.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
            viewHolder.binding.imageRight.description.setOnClickListener {
                val url = context.getString(data.description_id)
                val internetAct = Intent(Intent.ACTION_VIEW)
                internetAct.data = Uri.parse(url)
                context.startActivity(internetAct)
            }
            viewHolder.binding.imageRight.image.setImageResource(data.image)
            viewHolder.binding.imageLeft.linearLayout1.visibility = View.INVISIBLE
            viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
        } else {
            viewHolder.binding.imageRight.description.text = context.getString(data.description_id)
            viewHolder.binding.imageRight.image.setImageResource(data.image)
            viewHolder.binding.imageLeft.linearLayout1.visibility = View.INVISIBLE
            viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE

            // if card type == 3, set text to red => this is a fact
            if (data.cardType == 3) {
                viewHolder.binding.imageRight.description.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return group1.size
    }

    class ViewHolder(binding: GroupCardV1Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: GroupCardV1Binding = binding
    }
}