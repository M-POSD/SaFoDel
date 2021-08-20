package com.example.safodel.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.fragment.menuB.HomeFragmentDirections


class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        defaultViewSetup()

        binding.tip1Card.card.setOnClickListener() {
            val action = Epic1FragmentDirections.actionEpic1FragmentToTip1Fragment()
            findNavController().navigate(action)
        }

        setToolbarReturn(toolbar)
        return binding.root
    }

    private fun defaultViewSetup() {
        binding.warningCard.editText.text = "You are responsible for your own safety while delivering food."
        binding.tip1Card.editText.text = "Tip 1"
        binding.tip2Card.editText.text = "Tip 2"
        binding.tip3Card.editText.text = "Tip 3"
        binding.tip4Card.editText.text = "Tip 4"
        binding.epic1Picture.epicImage.setImageResource(R.drawable.epic1_image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}