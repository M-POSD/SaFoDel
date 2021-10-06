package com.example.safodel.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpicStyle2Binding
import com.example.safodel.model.GroupCard2Data

class EpicStyle2Adapter(val context: Context, group2Data: MutableList<GroupCard2Data>):
    RecyclerView.Adapter<EpicStyle2Adapter.ViewHolder>() {
    private var group2: MutableList<GroupCard2Data> = group2Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentEpicStyle2Binding =
            FragmentEpicStyle2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data: GroupCard2Data = group2[position]

        // set the default for all view visible
        viewHolder.binding.imageRight.linearLayout2.visibility = View.VISIBLE
        viewHolder.binding.pureText.linearLayout3.visibility = View.VISIBLE
        viewHolder.binding.imageRight.statistics.visibility = View.VISIBLE
        viewHolder.binding.imageRight.statistics.visibility = View.GONE

        when(data.cardType) {

            // 4 for for display pure notification only without any image
            4 -> {
                viewHolder.binding.pureText.description.text = context.getString(data.description_id)
//                if (data.title_id != 0) {
//                    viewHolder.binding.pureText.description2.text = context.getString(data.title_id)
//                    viewHolder.binding.pureText.description2.visibility = View.VISIBLE
//                }
                viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
            }

            // 5 for the information contains links
            5 -> {
                viewHolder.binding.imageRight.statistics.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
                viewHolder.binding.imageRight.statistics.setOnClickListener {
                    val url = context.getString(data.statistics_id)
                    val internetAct = Intent(Intent.ACTION_VIEW)
                    internetAct.data = Uri.parse(url)
                    context.startActivity(internetAct)
                }
                viewHolder.binding.imageRight.statistics.text = context.getString(R.string.click_to_know_more)
                viewHolder.binding.imageRight.statistics.gravity = Gravity.CENTER
                viewHolder.binding.imageRight.statistics.setTextColor(ContextCompat.getColor(context, R.color.black))
                viewHolder.binding.imageRight.title.text = context.getString(data.title_id)
                viewHolder.binding.imageRight.description.text = context.getString(data.description_id)
                viewHolder.binding.imageRight.image.setImageResource(data.image)
                viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
            }

            // all other information display (contains an image)
            else -> {
                viewHolder.binding.imageRight.title.text = context.getString(data.title_id)

                // if statistics_id is invalid -> set view is gone
                when {
                    data.statistics_id == 0 -> {
                        viewHolder.binding.imageRight.statistics.visibility = View.GONE
                    }
                    context.getString(data.statistics_id).isEmpty() -> {
                        viewHolder.binding.imageRight.statistics.visibility = View.GONE
                    }
                    else -> {
                        viewHolder.binding.imageRight.statistics.text = context.getString(data.statistics_id)
                    }
                }

                viewHolder.binding.imageRight.description.text = context.getString(data.description_id)
                viewHolder.binding.imageRight.image.setImageResource(data.image)
                viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount() = group2.size

    class ViewHolder(val binding: FragmentEpicStyle2Binding) : RecyclerView.ViewHolder(binding.root)
}