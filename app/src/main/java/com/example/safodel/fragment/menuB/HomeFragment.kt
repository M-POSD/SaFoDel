package com.example.safodel.fragment.menuB

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment
import android.view.animation.*
import android.widget.ImageView
import android.widget.Toolbar
import androidx.core.animation.addListener
import androidx.core.view.isVisible


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        binding.epicCard12.editTextLeft.text = "Ride Safer"
        binding.epicCard12.editTextRight.text = "E-Bike Delivery"
        binding.epicCard34.editTextLeft.text = "Safety Gears"
//        binding.epicCard34.editTextRight.text = "Placeholder"

        binding.epicCard12.cardLeft.setOnClickListener() {
            recordPosition(0)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        binding.epicCard12.cardRight.setOnClickListener() {
            recordPosition(1)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        binding.epicCard34.cardLeft.setOnClickListener() {
            recordPosition(2)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        configModeTheme()
        imageAnimations()

        return binding.root
    }

    private fun helmetAnimation() {
        val slideIn: Animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_in_top)
        slideIn.interpolator = AccelerateDecelerateInterpolator()
        slideIn.duration = 3000

        val animation = AnimationSet(false)
        animation.addAnimation(slideIn)
        animation.repeatCount = 1;
        binding.helmet.animation = animation
    }

    private fun backpackAnimation() {
        val slideIn: Animation =
            AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_in_left)
        slideIn.interpolator = AccelerateDecelerateInterpolator()
        slideIn.duration = 3000

        val animation = AnimationSet(false)

        animation.addAnimation(slideIn)
        animation.repeatCount = 1;
        binding.backpack.animation = animation
    }

    // the animation for all images
    private fun imageAnimations() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.backpack, "translationX", -100f, 68f)
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.backpack, "alpha", 0f, 1f)
        var objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.helmet, "translationY", -120f, 0f)
        var objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.helmet, "alpha", 0f, 1f)
        var objectAnimator5: ObjectAnimator = ObjectAnimator.ofFloat(binding.headlight, "alpha", 0f, 1f).setDuration(500)

        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3)
            .before(objectAnimator4)
        animatorSet.duration = 1000

        if (binding.headlight.isVisible) {
            objectAnimator4.addListener(
                onEnd = {
                    val animatorSet = AnimatorSet()
                    animatorSet.play(objectAnimator5)
                    animatorSet.start()
                })
        }


        animatorSet.start()

        // after animation 4 -> set images clickable
//        objectAnimator4.addListener(
//            onEnd = {
//                binding.images.setOnClickListener{
//                    imagesDrivingAnimation()
//                }
//            }
//        )
        binding.images.setOnClickListener{
            imagesDrivingAnimation()
        }
    }

    private fun imagesDrivingAnimation() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.images, "translationX", 0f, 800f)
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.images, "translationX", -800f, 0f)
        objectAnimator1.duration = 1500
        objectAnimator2.duration = 1500
        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator1).before(objectAnimator2)
        animatorSet.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // record the button position clicked to match the tab selected next page
    private fun recordPosition(position: Int) {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "epicPosition", Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putString("epicPosition", "" + position)
        spEditor.apply()
    }

    private fun configModeTheme() {
        binding.lightMode.setOnClickListener {
            binding.lightMode.visibility = View.INVISIBLE
            binding.darkMode.visibility = View.VISIBLE
            binding.coordinatorLayout.setBackgroundResource(R.color.gray)
            binding.headlight.visibility = View.VISIBLE
            binding.backpack.alpha = 0f
            binding.helmet.alpha = 0f
            binding.headlight.alpha = 0f
            imageAnimations()
            val toolbar = binding.toolbar.root
            setToolbarWhite(toolbar)
            Log.d("config", binding.headlight.isVisible.toString())
        }


        binding.darkMode.setOnClickListener {
            binding.darkMode.visibility = View.INVISIBLE
            binding.lightMode.visibility = View.VISIBLE
            binding.coordinatorLayout.setBackgroundResource(R.color.white)
            binding.headlight.visibility = View.INVISIBLE
            binding.backpack.alpha = 0f
            binding.helmet.alpha = 0f
            binding.headlight.alpha = 0f
            imageAnimations()
            val toolbar = binding.toolbar.root
            setToolbarBasic(toolbar)
        }
    }
}