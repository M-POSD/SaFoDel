package com.example.safodel.fragment.menuB

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Outline
import android.graphics.Path
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

        /* -- draw shadow light to the backpack--*/
        binding.backpack.outlineProvider = object : ViewOutlineProvider(){
            override fun getOutline(view: View?, outline: Outline?) {
                val path = Path()
                path.moveTo(view!!.width.toFloat(), view.height.toFloat())
                path.lineTo(2 * view.width / 3.toFloat(), 2 * view.height /3.toFloat())
                path.lineTo(2 * view.width / 3.toFloat(), 0.toFloat())
                path.lineTo(0 .toFloat(), 0.toFloat())
                path.lineTo(0 .toFloat(), view.height /2.toFloat())
                path.close()
                outline!!.setConvexPath(path)
            }
        }


        /* -- draw shadow light to the headlight--*/
        binding.headlight.outlineProvider = object : ViewOutlineProvider(){
            override fun getOutline(view: View?, outline: Outline?) {
                val path = Path()
                path.moveTo(view!!.width.toFloat(), view.height.toFloat())
                path.lineTo(5 * view.width .toFloat(), 2 * view.height /3.toFloat())
                path.lineTo(5 * view.width .toFloat(), 10 * view.height /3.toFloat())
                path.lineTo(-view.width .toFloat(), 0.toFloat())
                path.lineTo(-view.width .toFloat(), view.height /2.toFloat())
                path.close()
                outline!!.setConvexPath(path)
            }
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

        /**
         * I am broken
         */
//        objectAnimator4.addListener(
//            onEnd = {
//                Log.d("onEnd", "i am the end")
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
            ObjectAnimator.ofFloat(binding.images, "translationX", 0f, 4 * (view?.width ?: 1500) / 5.toFloat())
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.images, "translationX", -4 * (view?.width ?: 1500) / 5.toFloat(), 0f)
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
            binding.coordinatorLayout.setBackgroundResource(R.color.darkSky)
            binding.headlight.visibility = View.VISIBLE
            binding.backpack.alpha = 0f
            binding.helmet.alpha = 0f
            binding.headlight.alpha = 0f
            imageAnimations()
            val toolbar = binding.toolbar.root
            setToolbarWhite(toolbar)
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