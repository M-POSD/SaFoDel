package com.example.safodel.fragment.menuB

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color.argb
import android.graphics.Outline
import android.graphics.Path
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import com.example.safodel.ui.main.MainActivity
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.effet.RippleEffect
import com.takusemba.spotlight.shape.*
import android.view.ViewGroup
import androidx.core.view.isVisible


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    // Basic value
    private lateinit var toast: Toast
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mainActivity: MainActivity


    private lateinit var animatorSetLight: AnimatorSet
    private lateinit var animatorSetNight: AnimatorSet
    private lateinit var animatorDriving: AnimatorSet

    // testing section -> spotlight function
    private var isBeginnerMode = false
    private var currentToast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        toast = Toast.makeText(requireActivity(), null, Toast.LENGTH_SHORT)
        toolbar = binding.toolbar.root
        mainActivity = activity as MainActivity

        animatorSetLight = AnimatorSet()
        animatorSetNight = AnimatorSet()
        animatorDriving = AnimatorSet()

        binding.epicCard12.editTextLeft.text = "Ride Safer"
        binding.epicCard12.editTextRight.text = "E-Bike Info"
        binding.epicCard34.editTextLeft.text = "Safety Gears"
        binding.epicCard34.editTextRight.text = "Accident"

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

        binding.epicCard34.cardRight.setOnClickListener() {
            recordPosition(3)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }


        setToolbarBasic(toolbar)
        imageAnimations()
        imagesDrivingAnimation()
        startAnimation("light")
        configModeTheme()

        isBeginnerMode = false

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.doOnPreDraw {
            if (isLearningMode()) {
                startSpotLight()
                requireActivity().applicationContext.getSharedPreferences(
                    "isLearningMode",
                    Context.MODE_PRIVATE
                ).edit().putBoolean("isLearningMode", false).apply()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // add animation for the individual image
    private fun imageAnimations() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.backpack, "translationX", -100f, 68f)
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.backpack, "alpha", 0f, 1f)
        var objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.helmet, "translationY", -120f, 0f)
        var objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.helmet, "alpha", 0f, 1f)
        var objectAnimator5: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.headlight, "alpha", 0f, 1f).setDuration(500)

        animatorSetLight.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3)
            .before(objectAnimator4)
        animatorSetLight.duration = 1000

        animatorSetNight.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3)
            .before(objectAnimator4)
        animatorSetNight.play(objectAnimator4).before(objectAnimator5)
        animatorSetNight.duration = 1000

    }

    // add driving functionalities for images
    private fun imagesDrivingAnimation() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.images,
                "translationX",
                0f,
                4 * (view?.width ?: 1500) / 5.toFloat()
            )
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.images,
                "translationX",
                -4 * (view?.width ?: 1500) / 5.toFloat(),
                0f
            )
        objectAnimator1.duration = 1500
        objectAnimator2.duration = 1500

        animatorDriving.play(objectAnimator1).before(objectAnimator2)
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

    // config the top section layout (white background section), added all necessary image and animation based on different mode
    private fun configModeTheme() {
        binding.lightMode.setOnClickListener {
            if (animatorSetLight.isRunning || animatorSetNight.isRunning || animatorDriving.isRunning) {

            } else {
                binding.lightMode.visibility = View.INVISIBLE
                binding.darkMode.visibility = View.VISIBLE
                binding.coordinatorLayout.setBackgroundResource(R.color.darkSky)
                binding.headlight.visibility = View.VISIBLE
                binding.backpack.alpha = 0f
                binding.backpack.setImageResource(R.drawable.backpack_light)
                binding.helmet.alpha = 0f
                binding.headlight.alpha = 0f
                startAnimation("night")
                setToolbarWhite(toolbar)
            }
        }

        binding.darkMode.setOnClickListener {
            if (animatorSetLight.isRunning || animatorSetNight.isRunning || animatorDriving.isRunning) {

            } else {
                binding.darkMode.visibility = View.INVISIBLE
                binding.lightMode.visibility = View.VISIBLE
                binding.coordinatorLayout.setBackgroundResource(R.color.white)
                binding.headlight.visibility = View.INVISIBLE
                binding.backpack.alpha = 0f
                binding.backpack.setImageResource(R.drawable.backpack)
                binding.helmet.alpha = 0f
                binding.headlight.alpha = 0f
                startAnimation("light")
                setToolbarBasic(toolbar)
            }
        }
    }

    // start all animation
    private fun startAnimation(mode: String) {
        when (mode) {
            "light" -> animatorSetLight.start()
            "night" -> animatorSetNight.start()
        }

        binding.images.setOnClickListener {
            if (animatorDriving.isRunning) {
                animatorDriving.cancel()
                animatorDriving.start()
            }
            if (animatorSetLight.isRunning || animatorSetNight.isRunning) {

            } else {
                animatorDriving.start()
            }
        }
    }

    // for the learning mode for the beginner of the application
    private fun startSpotLight() {

        val targets = ArrayList<Target>()

        // first target
        val firstRoot = FrameLayout(requireContext())
        val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
        first.findViewById<TextView>(R.id.custom_text).text =
            "This is home page for user to return home easily"
        val firstTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navHome))
            .setShape(Circle(120f))
            .setOverlay(first)
            .build()
        targets.add(firstTarget)

        // second target
        val secondRoot = FrameLayout(requireContext())
        val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
        second.findViewById<TextView>(R.id.custom_text).text =
            "This is Map page to see the historical accident locations in Victoria"
        val secondTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navMap))
            .setShape(Circle(120f))
            .setOverlay(second)
            .build()
        targets.add(secondTarget)

        // third target
        val thirdRoot = FrameLayout(requireContext())
        val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
        third.findViewById<TextView>(R.id.custom_text).text =
            "These four buttons in the home page allow you to find all necessary information for e-bike delivering"
        val thirdTarget = Target.Builder()
            .setAnchor(binding.epicCard12.cardLeft)
            .setShape(
                RoundedRectangle(
                    (view?.height ?: 2000) / 7.toFloat(),
                    (view?.width ?: 1000) / 2.toFloat(),
                    10f
                )
            )
            .setOverlay(third)
            .build()

        targets.add(thirdTarget)

        // fourth target
        val fourthRoot = FrameLayout(requireContext())
        val fourth = layoutInflater.inflate(R.layout.layout_target, fourthRoot)
        fourth.findViewById<TextView>(R.id.custom_text).text =
            "Left Menu has quiz, graph or know more about us ~lol~"
        fourth.findViewById<TextView>(R.id.next_target).alpha = 0f
        val fourthTarget = Target.Builder()
            .setAnchor(80f, 120f)
            .setShape(Circle(110f))
            .setOverlay(fourth)
            .build()
//            .setEffect(RippleEffect(110f, 200f, argb(30, 124, 255, 90)))

        targets.add(fourthTarget)


        // create spotlight
        val spotlight = Spotlight.Builder(requireActivity())
            .setTargets(targets)
            .setBackgroundColorRes(R.color.spotlightBackground)
            .setDuration(1000L)
            .setAnimation(DecelerateInterpolator(2f))
            .setOnSpotlightListener(object : OnSpotlightListener {
                override fun onStarted() {
                    currentToast?.cancel()
                    currentToast = Toast.makeText(
                        requireContext(),
                        "Learning mode Begins",
                        Toast.LENGTH_SHORT
                    )
                    currentToast?.show()
                }

                override fun onEnded() {
                    currentToast?.cancel()
                    currentToast = Toast.makeText(
                        mainActivity,
                        "Learning Mode Ends",
                        Toast.LENGTH_SHORT
                    )
                    currentToast?.show()
                }
            })
            .build()

        binding.homeCoordinatorLayout1.setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navHome).setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navMap).setAllEnabled(false)

        spotlight.start()


        val nextTarget = View.OnClickListener {
            spotlight.next()
        }

        val closeSpotlight = View.OnClickListener {
            spotlight.finish()
            binding.homeCoordinatorLayout1.setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navHome).setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navMap).setAllEnabled(true)
        }

        first.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        third.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)

        first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fourth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
    }

    // referred from https://stackoverflow.com/questions/6238881/how-to-disable-all-click-events-of-a-layout
    // -> disable the view and its view group
    private fun View.setAllEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
    }

    private fun isLearningMode(): Boolean {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "isLearningMode",
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean("isLearningMode", false)
    }
}