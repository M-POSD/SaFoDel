package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import android.view.animation.*
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic1Binding
import com.example.safodel.fragment.BasicFragment

class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater,container,false)

        setDefaultView()

        binding.tip1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.tip1Fragment, null, navAnimationLeftToRight())
        }

        binding.tip2Card.card.setOnClickListener() {
            findNavController().navigate(R.id.tip2Fragment, null, navAnimationLeftToRight())
        }

        contentsAnimation()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDefaultView() {
        binding.tip1Card.title.text = "Safety Tips - while riding a bicycle"
        binding.tip2Card.title.text = "Safety Tips - for delivering at night"
//        binding.tip3Card.title.text = "Nearby Accident Prone areas"
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
}