package com.example.safodel.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpicStyle1Binding
import com.example.safodel.model.GroupCard1Data

class EpicStyle1Adapter(val context: Context, group1Data: MutableList<GroupCard1Data>):
    RecyclerView.Adapter<EpicStyle1Adapter.ViewHolder>() {
    private val group1: MutableList<GroupCard1Data> = group1Data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentEpicStyle1Binding =
            FragmentEpicStyle1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data: GroupCard1Data = group1[position]

        // set the default for all view visible
        viewHolder.binding.foodDeliveryImage.linearLayout1.visibility = View.VISIBLE
        viewHolder.binding.imageRight.linearLayout2.visibility = View.VISIBLE
        viewHolder.binding.pureText.linearLayout3.visibility = View.VISIBLE

        when(data.cardType) {

            // 0 for display pure notification only without any image
            0 -> {
                viewHolder.binding.foodDeliveryImage.linearLayout1.visibility = View.INVISIBLE
                viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
                viewHolder.binding.pureText.description.text = context.getString(data.description_id)
            }

            // -1 for the information contains links
            -1 -> {
                viewHolder.binding.foodDeliveryImage.uberImage.setOnClickListener {
                    val url = context.getString(R.string.uber_link)
                    val internetAct = Intent(Intent.ACTION_VIEW)
                    internetAct.data = Uri.parse(url)
                    context.startActivity(internetAct)
                }
                viewHolder.binding.foodDeliveryImage.deliverooImage.setOnClickListener {
                    val url = context.getString(R.string.deliveroo_link)
                    val internetAct = Intent(Intent.ACTION_VIEW)
                    internetAct.data = Uri.parse(url)
                    context.startActivity(internetAct)
                }
                viewHolder.binding.foodDeliveryImage.doorDashImage.setOnClickListener {
                    val url = context.getString(R.string.doordash_link)
                    val internetAct = Intent(Intent.ACTION_VIEW)
                    internetAct.data = Uri.parse(url)
                    context.startActivity(internetAct)
                }
                viewHolder.binding.foodDeliveryImage.easiImage.setOnClickListener {
                    val url = context.getString(R.string.easi_link)
                    val internetAct = Intent(Intent.ACTION_VIEW)
                    internetAct.data = Uri.parse(url)
                    context.startActivity(internetAct)
                }

//                viewHolder.binding.imageRight.description.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
//                viewHolder.binding.imageRight.description.setOnClickListener {
//                    val url = context.getString(data.description_id)
//                    val internetAct = Intent(Intent.ACTION_VIEW)
//                    internetAct.data = Uri.parse(url)
//                    context.startActivity(internetAct)
//                }
//                viewHolder.binding.imageRight.image.setImageResource(data.image)
                viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
                viewHolder.binding.imageRight.linearLayout2.visibility = View.INVISIBLE
            }

            // all other information display (contains an image)
            else -> {
                viewHolder.binding.foodDeliveryImage.linearLayout1.visibility = View.INVISIBLE
                viewHolder.binding.pureText.linearLayout3.visibility = View.INVISIBLE
                viewHolder.binding.imageRight.description.text = context.getString(data.description_id)
                viewHolder.binding.imageRight.image.setImageResource(data.image)

                // if card type == 3, set text to red => this is a fact
                if (data.cardType == 3) {
                    viewHolder.binding.imageRight.description.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.wrong_border_color
                        )
                    )
                }
            }
        }
    }

    override fun getItemCount() = group1.size

    class ViewHolder(binding: FragmentEpicStyle1Binding) : RecyclerView.ViewHolder(binding.root) {
        val binding: FragmentEpicStyle1Binding = binding
    }
}