package com.example.safodel.fragment.menuB

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.*
import android.view.ViewGroup
import com.example.safodel.retrofit.RetrofitClient
import com.example.safodel.retrofit.RetrofitInterface
import com.example.safodel.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    // get weather
    private val APP_ID = "898ef19b846722554449f6068e7c7253"
    private val CITY_NAME = "Caulfield"
    private lateinit var weatherService: RetrofitInterface

    // Basic value
    private lateinit var toast: Toast
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mainActivity: MainActivity

    private lateinit var animatorSetLight: AnimatorSet
    private lateinit var animatorSetNight: AnimatorSet
    private lateinit var animatorDriving: AnimatorSet
    private lateinit var rainingList: IntArray

    private var isBeginnerMode = false
    private var currentToast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        weatherService = RetrofitClient.getRetrofitService()
        callWeatherService()

        toast = Toast.makeText(requireActivity(), null, Toast.LENGTH_SHORT)
        toolbar = binding.toolbar.root
        mainActivity = activity as MainActivity

        animatorSetLight = AnimatorSet()
        animatorSetNight = AnimatorSet()
        animatorDriving = AnimatorSet()
        rainingList = IntArray(1)

        configDefaultTextView()
        configOnClickListener()
        setToolbarBasic(toolbar)
        imageAnimations()
        imagesDrivingAnimation()
        if (getCurrentTime() > 18 || getCurrentTime() < 7) {
            configTheme("night")
        } else {
            configTheme("light")
        }

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

    // set up default text view
    private fun configDefaultTextView() {
        binding.epicCard12.editTextLeft.text = "Ride Safer"
        binding.epicCard12.editTextRight.text = "E-Bike Info"
        binding.epicCard34.editTextLeft.text = "Safety Gears"
        binding.epicCard34.editTextRight.text = "Accident"
    }

    // raining animation
    private fun rainingAnimation(rainingImage: Int) {
//        binding.vusik.stopNotesFall()
        rainingList[0] = rainingImage
        binding.vusik.setImages(rainingList).start()
        binding.vusik.startNotesFall()
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

    // config onClickListener for navigation
    private fun configOnClickListener() {
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

        binding.lightMode.setOnClickListener {
            if (!animatorSetLight.isRunning && !animatorSetNight.isRunning && !animatorDriving.isRunning) {
                configTheme("night")
            }
        }

        binding.darkMode.setOnClickListener {
            if (!animatorSetLight.isRunning && !animatorSetNight.isRunning && !animatorDriving.isRunning) {
                configTheme("light")
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

            if (!animatorSetLight.isRunning && !animatorSetNight.isRunning) {
                animatorDriving.start()
            }
        }
    }

    // for the learning mode for the beginner of the application
    // using open source git "spotlight" package
    private fun startSpotLight() {

        val targets = ArrayList<Target>()

        // first target
        val firstRoot = FrameLayout(requireContext())
        val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
        first.findViewById<TextView>(R.id.custom_text).text =
            "This is Home page for you to return to this home page easily"
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
            "This is Map page to see the historical accident locations in Victoria with details"
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
            "Find out how to ride safely while delivering food"
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
            "Find information on using e-bike for food delivery"
        val fourthTarget = Target.Builder()
            .setAnchor(binding.epicCard12.cardRight)
            .setShape(
                RoundedRectangle(
                    (view?.height ?: 2000) / 7.toFloat(),
                    (view?.width ?: 1000) / 2.toFloat(),
                    10f
                )
            )
            .setOverlay(fourth)
            .build()

        targets.add(fourthTarget)

        // fifth target
        val fifthRoot = FrameLayout(requireContext())
        val fifth = layoutInflater.inflate(R.layout.layout_target, fifthRoot)
        fifth.findViewById<TextView>(R.id.custom_text).text =
            "Find out the cycling gear you need to deliver safe"
        val fifthTarget = Target.Builder()
            .setAnchor(binding.epicCard34.cardLeft)
            .setShape(
                RoundedRectangle(
                    (view?.height ?: 2000) / 7.toFloat(),
                    (view?.width ?: 1000) / 2.toFloat(),
                    10f
                )
            )
            .setOverlay(fifth)
            .build()

        targets.add(fifthTarget)

        // sixth target
        val sixthRoot = FrameLayout(requireContext())
        val sixth = layoutInflater.inflate(R.layout.layout_target, sixthRoot)
        sixth.findViewById<TextView>(R.id.custom_text).text =
            "Find out the measure for handling the bike accident"
        val sixthTarget = Target.Builder()
            .setAnchor(binding.epicCard34.cardRight)
            .setShape(
                RoundedRectangle(
                    (view?.height ?: 2000) / 7.toFloat(),
                    (view?.width ?: 1000) / 2.toFloat(),
                    10f
                )
            )
            .setOverlay(sixth)
            .build()

        targets.add(sixthTarget)

        // seventh target
        val seventhRoot = FrameLayout(requireContext())
        val seventh = layoutInflater.inflate(R.layout.layout_target, seventhRoot)
        seventh.findViewById<TextView>(R.id.custom_text).text =
            "Left Menu has\n" +
                    "QUIZ ->\n" +
                    "Test your understanding of delivering on food\n\n" +
                    "ANALYSIS ->\n" +
                    "Check the historical data in different region, time and weather\n\n" +
                    "ABOUT APP ->\n" +
                    "Understand the purpose of this app\n\n" +
                    "ABOUT US ->\n" +
                    "Know more about the developer team"
        seventh.findViewById<TextView>(R.id.next_target).alpha = 0f
        val seventhTarget = Target.Builder()
            .setAnchor(binding.toolbar.simpleToolbar.getChildAt(1))   // get the image view position
            .setShape(Circle(110f))
            .setOverlay(seventh)
            .build()
//            .setEffect(RippleEffect(110f, 200f, argb(30, 124, 255, 90)))

        targets.add(seventhTarget)

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
                        "Welcome to the SaFoDel learning mode",
                        Toast.LENGTH_SHORT
                    )
                    currentToast?.show()
                }

                override fun onEnded() {
                    currentToast?.cancel()
                    currentToast = Toast.makeText(
                        mainActivity,
                        "Thanks for watching",
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
        fourth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        fifth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        sixth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)

        first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fourth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fifth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        sixth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        seventh.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)

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

    private fun callWeatherService() {
        val callAsync: Call<WeatherResponse> = weatherService.getCurrentWeatherData(
            CITY_NAME,
            APP_ID
        )

        callAsync.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(call: Call<WeatherResponse?>?, response: Response<WeatherResponse?>) {
                if (response.isSuccessful) {
                    val list = response.body()!!.weatherList
                    if (list!= null) {
                        val weather = list[0].main
                        if (weather == "Rain") {
                            rainingAnimation(R.drawable.drop_blue_v3)
                        }
                        Log.d("currentWeather", weather)
                    }
                } else {
                    Log.i("Error ", "Response failed")
                }
            }

            override fun onFailure(call: Call<WeatherResponse?>?, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // get current hour
    private fun getCurrentTime(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    private fun configTheme(mode: String) {
        when(mode) {
            "light" -> {
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
            "night" -> {
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
    }
}