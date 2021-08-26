package com.example.safodel.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.safodel.fragment.home.Epic1Fragment
import com.example.safodel.fragment.home.Epic2Fragment
import com.example.safodel.fragment.home.Epic3Fragment


class EpicViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, num: Int) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    val numOfTabs = num

    override fun getItemCount(): Int = numOfTabs

    // return the page according to tab position
    override fun createFragment(position: Int): Fragment {
        Log.d("createFragment", "" + position)
        return when(position) {
            0 -> Epic1Fragment()
            1 -> Epic2Fragment()
            2 -> Epic3Fragment()
            else -> Epic1Fragment()
        }
    }
}