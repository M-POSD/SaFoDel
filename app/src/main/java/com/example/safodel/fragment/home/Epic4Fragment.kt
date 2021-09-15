package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic4Binding
import com.example.safodel.fragment.BasicFragment

class Epic4Fragment : BasicFragment<FragmentEpic4Binding>(FragmentEpic4Binding::inflate) {
    private lateinit var toast: Toast
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic4Binding.inflate(inflater, container, false)

        configDefaultTextView()
        toast = Toast.makeText(requireActivity(), null, Toast.LENGTH_SHORT)

        binding.rule1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.accident1Fragment, null, navAnimationLeftToRight())
        }

        binding.rule2Card.card.setOnClickListener() {
            findNavController().navigate(R.id.accident2Fragment, null, navAnimationLeftToRight())
        }

        binding.rule3Card.card.setOnClickListener() {
            findNavController().navigate(R.id.accident3Fragment, null, navAnimationLeftToRight())
        }

        contentsAnimation()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configDefaultTextView() {
        binding.rule1Card.title.text = getString(R.string.in_an_accident1_name)
        binding.rule2Card.title.text = getString(R.string.in_an_accident2_name)
        binding.rule3Card.title.text = getString(R.string.in_an_accident3_name)
        binding.rule1Card.mediumCardImage.setImageResource(R.drawable.inanaccident1)
        binding.rule2Card.mediumCardImage.setImageResource(R.drawable.inanaccident2)
        binding.rule3Card.mediumCardImage.setImageResource(R.drawable.inanaccident3)
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

}