package com.example.safodel.fragment.menuBottom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import com.example.safodel.ui.main.MainActivity
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.*
import android.view.ViewGroup
import android.widget.*
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
    private val CITY_NAME = "Caulfield,AU"

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
    private var isRaining = false
    private var isInitialRainingAnimation = true


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
        rainingList[0] = R.drawable.drop_blue_v3

        configDefaultTextView()
        configDefaultImageView()
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
        binding.homepageButtonLayout.epicCard1.scEditText.text = getString(R.string.epic1_name)
        binding.homepageButtonLayout.epicCard2.scEditText.text = getString(R.string.epic2_name)
        binding.homepageButtonLayout.epicCard3.scEditText.text = getString(R.string.epic3_name)
        binding.homepageButtonLayout.epicCard4.scEditText.text = getString(R.string.epic4_name)
    }

    // set up default image view
    private fun configDefaultImageView() {
        binding.homepageButtonLayout.epicCard1.scImageView.setImageResource(R.drawable.tip)
        binding.homepageButtonLayout.epicCard2.scImageView.setImageResource(R.drawable.delivery_on_ebike)
        binding.homepageButtonLayout.epicCard3.scImageView.setImageResource(R.drawable.safety_gear)
        binding.homepageButtonLayout.epicCard4.scImageView.setImageResource(R.drawable.in_an_accident)
    }

    // raining animation
    private fun rainingAnimation() {
//        binding.vusik.stopNotesFall()
        if (isInitialRainingAnimation) {
            isInitialRainingAnimation = false
            isRaining = true
            binding.homePageImages.vusik.setImages(rainingList).start()
            binding.homePageImages.vusik.startNotesFall()
        } else {
            if (isRaining) {
                isRaining = false
                binding.homePageImages.vusik.pauseNotesFall()
                binding.homePageImages.vusik.visibility = View.INVISIBLE
            } else {
                isRaining = true
                binding.homePageImages.vusik.resumeNotesFall()
                binding.homePageImages.vusik.visibility = View.VISIBLE
            }
        }
    }

    // add animation for the individual image
    private fun imageAnimations() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.homePageImages.backpack, "translationX", -100f, binding.homePageImages.backpack.translationX)
        Log.d("height", binding.homeFragmentXML.width.toString())
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.homePageImages.backpack, "alpha", 0f, 1f)
        var objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.homePageImages.helmet, "translationY", -120f, binding.homePageImages.helmet.translationY)
        Log.d("width", binding.homeFragmentXML.height.toString())
        var objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.homePageImages.helmet, "alpha", 0f, 1f)
        var objectAnimator5: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.homePageImages.headlight, "alpha", 0f, 1f).setDuration(500)

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
                binding.homePageImages.images,
                "translationX",
                0f,
                4 * (view?.width ?: 1500) / 5.toFloat()
            )

        Log.d("width", view?.width.toString())
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.homePageImages.images,
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
        binding.homepageButtonLayout.epicCard1.scCard.setOnClickListener() {
            recordPosition(0)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        binding.homepageButtonLayout.epicCard2.scCard.setOnClickListener() {
            recordPosition(1)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        binding.homepageButtonLayout.epicCard3.scCard.setOnClickListener() {
            recordPosition(2)
            findNavController().navigate(R.id.epicsFragment, null, navAnimationLeftToRight())
        }

        binding.homepageButtonLayout.epicCard4.scCard.setOnClickListener() {
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

        binding.weatherLightMode.setOnClickListener {
            rainingAnimation()
        }
        binding.weatherDarkMode.setOnClickListener {
            rainingAnimation()
        }
    }

    // start all animation
    private fun startAnimation(mode: String) {
        when (mode) {
            "light" -> animatorSetLight.start()
            "night" -> animatorSetNight.start()
        }

        binding.homePageImages.images.setOnClickListener {
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

        // v0 target
        val v0Root = FrameLayout(requireContext())
        val v0 = layoutInflater.inflate(R.layout.layout_target, v0Root)
        v0.findViewById<TextView>(R.id.custom_text).text = getString(R.string.v0)

        val v0Target = Target.Builder()
            .setShape(Circle(0f))
            .setOverlay(v0)
            .build()
        targets.add(v0Target)

        // first target
        val firstRoot = FrameLayout(requireContext())
        val first = layoutInflater.inflate(R.layout.layout_target, firstRoot)
        first.findViewById<TextView>(R.id.custom_text).text = getString(R.string.first_target)
        val firstTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navHome))
            .setShape(Circle(120f))
            .setOverlay(first)
            .build()
        targets.add(firstTarget)

        // second target
        val secondRoot = FrameLayout(requireContext())
        val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
        second.findViewById<TextView>(R.id.custom_text).text = getString(R.string.second_target)

        val secondTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navMap))
            .setShape(Circle(120f))
            .setOverlay(second)
            .build()
        targets.add(secondTarget)

        // third target
        val thirdRoot = FrameLayout(requireContext())
        val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
        third.findViewById<TextView>(R.id.custom_text).text = getString(R.string.third_target)

        val thirdTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navAnalysis))
            .setShape(Circle(120f))
            .setOverlay(third)
            .build()
        targets.add(thirdTarget)

        // fourth target
        val fourthRoot = FrameLayout(requireContext())
        val fourth = layoutInflater.inflate(R.layout.layout_target, fourthRoot)
        fourth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.fourth_target)

        val fourthTarget = Target.Builder()
            .setAnchor(requireActivity().findViewById<View>(R.id.navExam))
            .setShape(Circle(120f))
            .setOverlay(fourth)
            .build()

        targets.add(fourthTarget)


        // fifth target
        val fifthRoot = FrameLayout(requireContext())
        val fifth = layoutInflater.inflate(R.layout.layout_target, fifthRoot)
        fifth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.fifth_target)

        val fifthTarget = Target.Builder()
            .setAnchor(binding.homepageButtonLayout.epicCard1.scCard)
            .setShape(
                RoundedRectangle(
                    binding.homepageButtonLayout.epicCard1.scCard.height * 1.2.toFloat(),
                    binding.homepageButtonLayout.epicCard1.scCard.width* 1.2.toFloat(),
                    10f
                )
            )
            .setOverlay(fifth)
            .build()

        targets.add(fifthTarget)


        // sixth target
        val sixthRoot = FrameLayout(requireContext())
        val sixth = layoutInflater.inflate(R.layout.layout_target, sixthRoot)
        sixth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.sixth_target)

        val sixthTarget = Target.Builder()
            .setAnchor(binding.homepageButtonLayout.epicCard1.scCard)
            .setShape(
                RoundedRectangle(
                    binding.homepageButtonLayout.epicCard2.scCard.height * 1.2.toFloat(),
                    binding.homepageButtonLayout.epicCard2.scCard.width* 1.2.toFloat(),
                    10f
                )
            )
            .setOverlay(sixth)
            .build()

        targets.add(sixthTarget)


        val height =  (binding.homepageButtonLayout.epicCard4.scCard.height
                       - binding.homepageButtonLayout.epicCard4.scCard.top*2).toFloat()

        // seventh target
        val seventhRoot = FrameLayout(requireContext())
        val seventh = layoutInflater.inflate(R.layout.layout_target, seventhRoot)
        seventh.findViewById<TextView>(R.id.custom_text).text = getString(R.string.seventh_target)

        val seventhTarget = Target.Builder()
            .setAnchor((binding.root.width/2).toFloat(),(requireActivity().findViewById<View>(R.id.bottom_navigation).top - height*3))
            .setShape(
                RoundedRectangle(
                    binding.homepageButtonLayout.epicCard3.scCard.height * 1.2.toFloat(),
                    binding.homepageButtonLayout.epicCard3.scCard.width* 1.2.toFloat(),
                    10f
                )
            )
            .setOverlay(seventh)
            .build()

        targets.add(seventhTarget)



        // eighth target
        val eighthRoot = FrameLayout(requireContext())
        val eighth = layoutInflater.inflate(R.layout.layout_target, eighthRoot)
        eighth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.eighth_target)


        val eighthTarget = Target.Builder()
            .setAnchor((binding.root.width/2).toFloat(),(requireActivity().findViewById<View>(R.id.bottom_navigation).top - height))
            .setShape(
                RoundedRectangle(
                    binding.homepageButtonLayout.epicCard4.scCard.height * 1.2.toFloat(),
                    binding.homepageButtonLayout.epicCard4.scCard.width* 1.2.toFloat(),
                    10f
                )
            )
            .setOverlay(eighth)
            .build()

        targets.add(eighthTarget)

        // ninth target
        val ninthRoot = FrameLayout(requireContext())
        val ninth = layoutInflater.inflate(R.layout.layout_target, ninthRoot)
        ninth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.ninth_target)

        ninth.findViewById<TextView>(R.id.next_target).alpha = 0f
        val ninthTarget = Target.Builder()
            .setAnchor(binding.toolbar.simpleToolbar.getChildAt(1))   // get the image view position
            .setShape(Circle(110f))
            .setOverlay(ninth)
            .build()

        targets.add(ninthTarget)

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
                        getString(R.string.start_learning_mode),
                        Toast.LENGTH_SHORT
                    )
                    currentToast?.show()
                }

                override fun onEnded() {
                    currentToast?.cancel()
                    currentToast = Toast.makeText(
                        mainActivity,
                        getString(R.string.end_learning_mode),
                        Toast.LENGTH_SHORT
                    )
                    currentToast?.show()
                }
            })
            .build()

        binding.homeCoordinatorLayout1.setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navHome).setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navMap).setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navExam).setAllEnabled(false)
        requireActivity().findViewById<View>(R.id.navAnalysis).setAllEnabled(false)

        spotlight.start()

        val nextTarget = View.OnClickListener {
            spotlight.next()
        }

        val closeSpotlight = View.OnClickListener {
            binding.homeScrollView.fullScroll(ScrollView.FOCUS_UP)
            spotlight.finish()
            binding.homeCoordinatorLayout1.setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navHome).setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navMap).setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navExam).setAllEnabled(true)
            requireActivity().findViewById<View>(R.id.navAnalysis).setAllEnabled(true)
        }

        v0.findViewById<View>(R.id.next_target).setOnClickListener{
            binding.homeScrollView.scrollTo(0,0)
            spotlight.next()
        }
        first.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        third.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        fourth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        var scroll = binding.homepageButtonLayout.epicCard1.scCard.top + binding.homepageButtonLayout.epicCard1.scCard.bottom
        fifth.findViewById<View>(R.id.next_target).setOnClickListener {
            binding.homeScrollView.scrollTo(0,scroll)
            spotlight.next()
        }
        sixth.findViewById<View>(R.id.next_target).setOnClickListener {
            binding.homeScrollView.fullScroll(ScrollView.FOCUS_DOWN)
            spotlight.next()
        }
        seventh.findViewById<View>(R.id.next_target).setOnClickListener{
            spotlight.next()
        }
        eighth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)

        v0.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fourth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fifth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        sixth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        seventh.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        eighth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        ninth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)

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
            override fun onResponse(
                call: Call<WeatherResponse?>?,
                response: Response<WeatherResponse?>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()!!.weatherList
                    if (list != null) {
                        val weather = list[0].main
                        if (weather == "Rain") {
                            rainingAnimation()
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

    // theme based on light or night
    private fun configTheme(mode: String) {
        when (mode) {
            "light" -> {
                binding.darkModeLayout.visibility = View.INVISIBLE
                binding.lightModeLayout.visibility = View.VISIBLE
                binding.homePageImages.coordinatorLayout.setBackgroundResource(R.color.white)
                binding.homePageImages.headlight.visibility = View.INVISIBLE
                binding.homePageImages.backpack.alpha = 0f
                binding.homePageImages.backpack.setImageResource(R.drawable.backpack_light)
                binding.homePageImages.helmet.alpha = 0f
                binding.homePageImages.headlight.alpha = 0f
                binding.homePageImages.groundForDriver.visibility = View.VISIBLE
                startAnimation("light")
                setToolbarBasic(toolbar)
            }
            "night" -> {
                binding.lightModeLayout.visibility = View.INVISIBLE
                binding.darkModeLayout.visibility = View.VISIBLE
                binding.homePageImages.coordinatorLayout.setBackgroundResource(R.color.darkSky)
                binding.homePageImages.headlight.visibility = View.VISIBLE
                binding.homePageImages.backpack.alpha = 0f
                binding.homePageImages.backpack.setImageResource(R.drawable.backpack_dark)
                binding.homePageImages.helmet.alpha = 0f
                binding.homePageImages.headlight.alpha = 0f
                binding.homePageImages.groundForDriver.visibility = View.INVISIBLE
                startAnimation("night")
                setToolbarWhite(toolbar)
            }
        }
    }

    // currently this is just for getting Rain weather icons(different mode light/night)
    private fun configWeatherIcons(): IntArray {
        var currentWeatherIcons = IntArray(2)

        when ("Rain") {
            "Clear" -> {
                currentWeatherIcons[0] = R.drawable.clear_black
                currentWeatherIcons[1] = R.drawable.clear_white
            }
            "Rain" -> {
                currentWeatherIcons[0] = R.drawable.rain_black
                currentWeatherIcons[1] = R.drawable.rain_white
            }
            else -> {
                currentWeatherIcons[0] = R.drawable.clouds_black
                currentWeatherIcons[1] = R.drawable.clouds_white
            }
        }

        return currentWeatherIcons
    }
}