package com.example.safodel.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.safodel.fragment.home.Epic1Fragment
import com.example.safodel.fragment.home.Epic2Fragment
import com.example.safodel.fragment.home.Epic3Fragment


class EpicViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        Log.d("createFragment", "" + position)
        return when(position) {
            1 -> Epic2Fragment()
            2 -> Epic3Fragment()
            else -> Epic1Fragment()
        }
    }
}