package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic3Binding
import com.example.safodel.fragment.BasicFragment

class Epic3Fragment : BasicFragment<FragmentEpic3Binding>(FragmentEpic3Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic3Binding.inflate(inflater, container, false)

        configDefaultTextView()

        binding.gear1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.gear1Fragment, null, navAnimationLeftToRight())
        }

        binding.gear2Card.card.setOnClickListener() {
            findNavController().navigate(R.id.gear2Fragment, null, navAnimationLeftToRight())
        }

        binding.gear3Card.card.setOnClickListener() {
            findNavController().navigate(R.id.gear3Fragment, null, navAnimationLeftToRight())
        }

        contentsAnimation()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configDefaultTextView() {
        binding.gear1Card.title.text = getString(R.string.gear1_name)
        binding.gear2Card.title.text = getString(R.string.gear2_name)
        binding.gear3Card.title.text = getString(R.string.gear3_name)
    }

    // contents animation slide in from bottom
    private fun contentsAnimation() {
        val slideIn: Animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_in_bottom)
        slideIn.interpolator = AccelerateDecelerateInterpolator()
        slideIn.duration = 1500

        val animation = AnimationSet(false)
        animation.addAnimation(slideIn)
        animation.repeatCount = 1;
        binding.epicLayout.animation = animation
    }

}