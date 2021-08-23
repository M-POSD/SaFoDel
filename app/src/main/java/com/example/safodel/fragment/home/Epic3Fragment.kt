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
        _binding = FragmentEpic3Binding.inflate(inflater,container,false)

        setDefaultView()

        binding.gear1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.gear1Fragment, null, navAnimationLeftToRight())
        }

        layoutAnimation()

        return binding.root
    }

    private fun setDefaultView() {
        binding.gear1Card.title.text = "Gear 1"
        binding.gear1Card.subtitle.text = "safety gear should wear while delivering"
        binding.gear2Card.title.text = "Gear 2"
        binding.gear2Card.subtitle.text = "a detailed checklist of necessary safety equipment"
        binding.gear3Card.title.text = "Gear 3"
        binding.gear3Card.subtitle.text = "Australian standards for the safety gear "
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