package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic4Binding
import com.example.safodel.fragment.BasicFragment

class Epic4Fragment : BasicFragment<FragmentEpic4Binding>(FragmentEpic4Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic4Binding.inflate(inflater, container, false)

        setDefaultView()

        binding.rule1Card.card.setOnClickListener() {
//            findNavController().navigate(R.id.gear1Fragment, null, navAnimationLeftToRight())
        }

        binding.rule2Card.card.setOnClickListener() {
//            findNavController().navigate(R.id.gear2Fragment, null, navAnimationLeftToRight())
        }

        binding.rule3Card.card.setOnClickListener() {
//            findNavController().navigate(R.id.gear3Fragment, null, navAnimationLeftToRight())
        }

        contentsAnimation()

        return binding.root
    }

    private fun setDefaultView() {
        binding.rule1Card.title.text = "PLACEHOLDER"
        binding.rule2Card.title.text = "PLACEHOLDER"
        binding.rule3Card.title.text = "PLACEHOLDER"
    }

    // contents animation slide in from bottom
    private fun contentsAnimation() {
        val slideIn: Animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_in_bottom)
        slideIn.interpolator = AccelerateDecelerateInterpolator()
        slideIn.duration = 1500

        val animation = AnimationSet(false)
        animation.addAnimation(slideIn)
        animation.repeatCount = 1
        binding.epicLayout.animation = animation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}