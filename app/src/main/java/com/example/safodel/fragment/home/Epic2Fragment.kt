package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic2Binding
import com.example.safodel.fragment.BasicFragment

class Epic2Fragment : BasicFragment<FragmentEpic2Binding>(FragmentEpic2Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic2Binding.inflate(inflater,container,false)

        setDefaultView()

        binding.info1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.info1Fragment, null, navAnimationLeftToRight())
        }

        binding.info2Card.card.setOnClickListener() {
            findNavController().navigate(R.id.info2Fragment, null, navAnimationLeftToRight())
        }

        binding.info3Card.card.setOnClickListener() {
            findNavController().navigate(R.id.info3Fragment, null, navAnimationLeftToRight())
        }

        contentsAnimation()

        return binding.root
    }

    private fun setDefaultView() {
        binding.info1Card.title.text = "Risks with E-bikes"
        binding.info2Card.title.text = "Advantages of E-bikes delivering"
        binding.info3Card.title.text = "E-bikes Rules & Regulations"
    }

    // contents animation slide in from bottom
    private fun contentsAnimation() {
        val slideIn: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_in_bottom)
        slideIn.interpolator = AccelerateDecelerateInterpolator()
        slideIn.duration = 1500

        val animation = AnimationSet(false)
        animation.addAnimation(slideIn)
        animation.repeatCount = 1;
        binding.epicLayout.animation = animation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}