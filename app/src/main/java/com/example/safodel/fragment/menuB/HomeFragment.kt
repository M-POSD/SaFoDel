package com.example.safodel.fragment.menuB

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        binding.epicCard12.editTextLeft.text = "Ride Safer"
        binding.epicCard12.editTextRight.text = "Delivery on e-bike"
        binding.epicCard34.editTextLeft.text = "Safety Gear"
        binding.epicCard34.editTextRight.text = "Placeholder"

        binding.epicCard12.cardLeft.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpicsFragment()
            findNavController().navigate(action)
        }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}