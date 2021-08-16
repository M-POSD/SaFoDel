package com.example.safodel.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.safodel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}