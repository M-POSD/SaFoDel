package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        // if card type == 1, put the image on left, otherwise put it on right
        if (data.cardType == 1) {
            viewHolder.binding.imageLeft.description.text = data.description
            viewHolder.binding.imageLeft.image.setImageResource(data.image)
            viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
            viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
        } else if (data.cardType == 0) { // present text only
            viewHolder.binding.pureText.description.text = data.description
            viewHolder.binding.imageLeft.linearLayout1.visibility = View.INVISIBLE
            viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE

        } else {
            viewHolder.binding.imageRight.description.text = data.description
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

//viewHolder.binding.subtitle.text =
//            HtmlCompat.fromHtml(info.description, HtmlCompat.FROM_HTML_MODE_LEGACY)