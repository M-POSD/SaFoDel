package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.databinding.FragmentDeveloperBinding
import com.example.safodel.fragment.BasicFragment


class DeveloperFragment : BasicFragment<FragmentDeveloperBinding>(FragmentDeveloperBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeveloperBinding.inflate(inflater,container,false)
        binding.developerTeam.text = "Team 27"
        binding.developerTeam.textSize = 25.0F
        binding.developerName.text = "CJ" + "\n" +
                "Francis" + "\n" +
                "Hsuanyu" + "\n" +
                "James" + "\n" +
                "Suvansh"
        val toolbar = binding.toolbar.root
        setToolbarReturn(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}