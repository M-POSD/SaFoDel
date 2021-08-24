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

        layoutAnimation()

        return binding.root
    }

    private fun setDefaultView() {
        binding.info1Card.title.text = "Info 1"
        binding.info1Card.subtitle.text = "the potential risks of delivering"
        binding.info2Card.title.text = "Info 2"
        binding.info2Card.subtitle.text = "advantages of delivering"
        binding.info3Card.title.text = "Info 3"
        binding.info3Card.subtitle.text = "information on the e-bike rules and regulations"
    }

    private fun layoutAnimation() {
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