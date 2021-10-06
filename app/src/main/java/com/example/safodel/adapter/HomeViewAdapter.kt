package com.example.safodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHeroFeatureBinding
import com.example.safodel.ui.main.MainActivity
import com.example.safodel.viewModel.IsLearningModeViewModel

class HomeViewAdapter(val context: Context, private val parentFragment: Fragment):
    RecyclerView.Adapter<HomeViewAdapter.ViewHolder>() {
    private lateinit var learningModeModel: IsLearningModeViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FragmentHeroFeatureBinding =
            FragmentHeroFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        learningModeModel = ViewModelProvider(parentFragment.requireActivity()).get(IsLearningModeViewModel::class.java)
        mainActivity = parentFragment.activity as MainActivity
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        when(position) {
            // 0 -> for introduction home page view
            0 -> {
                setVisibility(viewHolder,0)
            }

            // 2 -> map page info intro with navigation
            1 -> {
                setVisibility(viewHolder,2)
                viewHolder.binding.heroFeatureInfo3.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.mapfragment)
                    mainActivity.callOnNav(1)
                }
            }

            // 3 -> history trend page intro with navigation
            2 -> {
                setVisibility(viewHolder,3)
                viewHolder.binding.heroFeatureInfo4.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.analysisFragment)
                    mainActivity.callOnNav(2)
                }
            }

            // 4 -> checklist page intro with navigation
            3 -> {
                setVisibility(viewHolder,4)
                viewHolder.binding.heroFeatureInfo5.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.checklistFragment)
                    mainActivity.callOnNav(4)
                }
            }

            // 5 -> quiz page intro with navigation
            4 -> {
                setVisibility(viewHolder,5)
                viewHolder.binding.heroFeatureInfo6.card.setOnClickListener{
                    parentFragment.findNavController().navigate(R.id.quizFragment)
                    mainActivity.callOnNav(3)
                }
            }
        }
        observeLearningMode(viewHolder)
    }

    override fun getItemCount() = 5

    /**
     * set view visible when the specific view is selected
     */
    private fun setVisibility(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.heroFeatureInfo1.intro1Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo2.intro2Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo3.intro3Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo4.intro4Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo5.intro5Layout.visibility = View.INVISIBLE
        viewHolder.binding.heroFeatureInfo6.intro6Layout.visibility = View.INVISIBLE

        // only when the selected view will be visible
        when(position) {
            0 -> viewHolder.binding.heroFeatureInfo1.intro1Layout.visibility = View.VISIBLE
            1 -> viewHolder.binding.heroFeatureInfo2.intro2Layout.visibility = View.VISIBLE
            2 -> viewHolder.binding.heroFeatureInfo3.intro3Layout.visibility = View.VISIBLE
            3 -> viewHolder.binding.heroFeatureInfo4.intro4Layout.visibility = View.VISIBLE
            4 -> viewHolder.binding.heroFeatureInfo5.intro5Layout.visibility = View.VISIBLE
            5 -> viewHolder.binding.heroFeatureInfo6.intro6Layout.visibility = View.VISIBLE
        }
    }

    private fun observeLearningMode(viewHolder: ViewHolder) {
        learningModeModel.isLearningMode().observe(parentFragment.viewLifecycleOwner, { isLearningMode ->
               viewHolder.binding.heroFeatureInfo3.card.isEnabled = !isLearningMode
               viewHolder.binding.heroFeatureInfo4.card.isEnabled = !isLearningMode
               viewHolder.binding.heroFeatureInfo5.card.isEnabled = !isLearningMode
               viewHolder.binding.heroFeatureInfo6.card.isEnabled = !isLearningMode
        })
    }

    class ViewHolder(val binding: FragmentHeroFeatureBinding) : RecyclerView.ViewHolder(binding.root)
}