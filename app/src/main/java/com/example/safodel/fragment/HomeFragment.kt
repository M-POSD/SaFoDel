package com.example.safodel.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.ui.main.MainActivity


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val toolbar = binding.toolbar.root
        setToolbar(toolbar)

        binding.epic1Card.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpic1Fragment()
            findNavController().navigate(action)
        }

        binding.epic2Card.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpic2Fragment()
            findNavController().navigate(action)
        }

        binding.epic3Card.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpic3Fragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}