package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHeroFeatureBinding

class HomeViewAdapter(val context: Context, private val parentFragment: Fragment): RecyclerView.Adapter<HomeViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentHeroFeatureBinding =
            FragmentHeroFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when(position) {
            0 -> {
                setVisibility(viewHolder,0)
            }
            1 -> {
                setVisibility(viewHolder,1)
            }
            2 -> {
                setVisibility(viewHolder,2)
                viewHolder.binding.heroFeatureInfo3.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.mapfragment)
                }
            }
            3 -> {
                setVisibility(viewHolder,3)
                viewHolder.binding.heroFeatureInfo4.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.analysisFragment)
                }
            }
            4 -> {
                setVisibility(viewHolder,4)
                viewHolder.binding.heroFeatureInfo5.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.checklistFragment)
                }
            }
            5 -> {
                setVisibility(viewHolder,5)
                viewHolder.binding.heroFeatureInfo6.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.examFragment)
                }
            }
        }
    }

    override fun getItemCount() = 6

    private fun setVisibility(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.heroFeatureInfo1.intro1Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo2.intro2Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo3.intro3Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo4.intro4Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo5.intro5Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo6.intro6Layout.visibility = View.INVISIBLE
        when(position) {
            0 -> viewHolder.binding.heroFeatureInfo1.intro1Layout.visibility = View.VISIBLE
            1 -> viewHolder.binding.heroFeatureInfo2.intro2Layout.visibility = View.VISIBLE
            2 -> viewHolder.binding.heroFeatureInfo3.intro3Layout.visibility = View.VISIBLE
            3 -> viewHolder.binding.heroFeatureInfo4.intro4Layout.visibility = View.VISIBLE
            4 -> viewHolder.binding.heroFeatureInfo5.intro5Layout.visibility = View.VISIBLE
            5 -> viewHolder.binding.heroFeatureInfo6.intro6Layout.visibility = View.VISIBLE
        }
    }

    class ViewHolder(binding: FragmentHeroFeatureBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: FragmentHeroFeatureBinding = binding
    }
}