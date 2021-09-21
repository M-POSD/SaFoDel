package com.example.safodel.fragment.safodelStart

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.safodel.databinding.FragmentWelcomeSafodelBinding

class WelcomeSafoDelFragment: Fragment() {
    private var _binding: FragmentWelcomeSafodelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeSafodelBinding.inflate(inflater, container, false)
//        configAllAnimations()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null }

    // confi all animations in the start activity
    private fun configAllAnimations() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.welcomeInformation1.image, "translationX", 100f, 0f)
        var objectAnimator2: ObjectAnimator = ObjectAnimator.ofFloat(binding.welcomeInformation1.image, "alpha", 0f, 1f)

        objectAnimator1.duration = 1300
        objectAnimator2.duration = 1300

        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator1).with(objectAnimator2)
        animatorSet.start()
    }
}