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
        setToolbar(toolbar)

        binding.epic1Card.editText.text = "Ride Safer"
        binding.epic2Card.editText.text = "Delivery on e-bike"
        binding.epic3Card.editText.text = "Safty Gear"

        binding.epic1Card.card.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpic1Fragment()
            findNavController().navigate(action)
        }

        binding.epic2Card.card.setOnClickListener() {
            val action = HomeFragmentDirections.actionHomeFragmentToEpic2Fragment()
            findNavController().navigate(action)
        }

        binding.epic3Card.card.setOnClickListener() {
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