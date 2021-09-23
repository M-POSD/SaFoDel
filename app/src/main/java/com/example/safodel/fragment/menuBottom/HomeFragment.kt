package com.example.safodel.fragment.menuBottom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.core.widget.NestedScrollView
import android.view.MenuInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setFitsSystemWindows
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.adapter.EpicStyle2Adapter
import com.example.safodel.adapter.HomeViewAdapter
import com.example.safodel.databinding.HomepageButtonLayoutBinding
import com.example.safodel.databinding.HomepageImagesBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.concurrent.schedule
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.safodel.viewModel.WeatherViewModel
import java.lang.Exception


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    // get weather
    private val APP_ID = "898ef19b846722554449f6068e7c7253"
    private val CITY_NAME = "clayton,AU"

    private lateinit var weatherService: RetrofitInterface

    // Basic value
    private lateinit var toast: Toast
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mainActivity: MainActivity

    private lateinit var animatorSetLight: AnimatorSet
    private lateinit var animatorSetNight: AnimatorSet
    private lateinit var animatorDriving: AnimatorSet
    private lateinit var rainingList: IntArray
    private lateinit var homePageImage: HomepageImagesBinding
    private lateinit var homepageButtonLayout: HomepageButtonLayoutBinding

    private var isBeginnerMode = false
    private var currentToast: Toast? = null
    private var isRaining = false
    private var isInitialRainingAnimation = true

    private lateinit var adapter: HomeViewAdapter
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    private lateinit var model: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


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
        homePageImage = binding.homePageImages
        homepageButtonLayout = binding.homepageButtonLayout
        mainActivity = activity as MainActivity

        animatorSetLight = AnimatorSet()
        animatorSetNight = AnimatorSet()
        animatorDriving = AnimatorSet()
        rainingList = IntArray(1)
        rainingList[0] = R.drawable.drop_blue_v3

        configDefaultTextView()
        configDefaultImageView()
        configOnClickListener()

        imageAnimations()
        imagesDrivingAnimation()
//        if (getCurrentTime() > 18 || getCurrentTime() < 7) {
//            configTheme("night")
//
//        } else {
//            configTheme("light")
//        }

        configTheme("light")
        isBeginnerMode = false

        GlobalScope.launch {
            // try to get the height of status bar and then margin top
            val toolbarHeight = toolbar.layoutParams as CoordinatorLayout.LayoutParams
            while (toolbarHeight.topMargin == 0)
                toolbarHeight.topMargin = mainActivity.getStatusHeight()
            toolbar.layoutParams = toolbarHeight
            this.cancel()
        }

        binding.toolbar.simpleToolbar.fitsSystemWindows = false


        adapter = HomeViewAdapter(requireActivity(), this)
        homepageButtonLayout.viewPager2Home.adapter = adapter
        homepageButtonLayout.wormDotsIndicatorHome.setViewPager2(homepageButtonLayout.viewPager2Home)
        setViewPager2AutoIncrementPosition()

        model = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

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

    private fun setViewPager2AutoIncrementPosition() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (homepageButtonLayout.viewPager2Home.currentItem == 5) {
                homepageButtonLayout.viewPager2Home.currentItem -= 5
            } else {
//                Log.d(
//                    "current position",
//                    homepageButtonLayout.viewPager2Home.currentItem.toString()
//                )
                homepageButtonLayout.viewPager2Home.currentItem += 1
            }
            handler.postDelayed(runnable, 5000) //5 sec delay
        }
        handler.postDelayed(runnable, 5000)

        homepageButtonLayout.viewPager2Home.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                handler.removeCallbacks(runnable)
//                Log.e("onPageScrolled", position.toString())
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                handler.postDelayed(runnable, 5000)
//                Log.e("Selected_Page", position.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.nav_icon_menu_dark_mode, menu)
    }

    // set up default text view
    private fun configDefaultTextView() {
        homepageButtonLayout.epicCard12.scEditTextLeft.text = getString(R.string.epic1_name)
        homepageButtonLayout.epicCard12.scEditTextRight.text =
            getString(R.string.epic2_name)
        homepageButtonLayout.epicCard34.scEditTextLeft.text = getString(R.string.epic3_name)
        homepageButtonLayout.epicCard34.scEditTextRight.text =
            getString(R.string.epic4_name)
    }

    // set up default image view
    private fun configDefaultImageView() {
        homepageButtonLayout.epicCard12.scImageViewLeft.setImageResource(R.drawable.tip)
        homepageButtonLayout.epicCard12.scImageViewRight.setImageResource(R.drawable.delivery_on_ebike)
        homepageButtonLayout.epicCard34.scImageViewLeft.setImageResource(R.drawable.road_sign)
        homepageButtonLayout.epicCard34.scImageViewRight.setImageResource(R.drawable.in_an_accident)
    }

    // raining animation
    private fun rainingAnimation() {
//        binding.vusik.stopNotesFall()
        if (isInitialRainingAnimation) {
            isInitialRainingAnimation = false
            isRaining = true
            homePageImage.vusik.setImages(rainingList).start()
            homePageImage.vusik.startNotesFall()
        } else {
            if (isRaining) {
                isRaining = false
                homePageImage.vusik.pauseNotesFall()
                homePageImage.vusik.visibility = View.INVISIBLE
            } else {
                isRaining = true
                homePageImage.vusik.resumeNotesFall()
                homePageImage.vusik.visibility = View.VISIBLE
            }
        }
    }

    // add animation for the individual image
    private fun imageAnimations() {
        var objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.backpack,
                "translationX",
                -100f,
                homePageImage.backpack.translationX
            )
        Log.d("height", binding.homeFragmentXML.width.toString())
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(homePageImage.backpack, "alpha", 0f, 1f)
        var objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.helmet,
                "translationY",
                -120f,
                homePageImage.helmet.translationY
            )
        Log.d("width", binding.homeFragmentXML.height.toString())
        var objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(homePageImage.helmet, "alpha", 0f, 1f)
        var objectAnimator5: ObjectAnimator =
            ObjectAnimator.ofFloat(homePageImage.headlight, "alpha", 0f, 1f)
                .setDuration(500)

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
                homePageImage.images2,
                "translationX",
                0f,
                4 * (view?.width ?: 1500) / 5.toFloat()
            )

        Log.d("width", view?.width.toString())
        var objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.images2,
                "translationX",
                -4 * (view?.width ?: 1500) / 5.toFloat(),
                0f
            )
        objectAnimator1.duration = 1500
        objectAnimator2.duration = 1500

        animatorDriving.play(objectAnimator1).before(objectAnimator2)
        animatorDriving.doOnEnd {
            homePageImage.images.visibility = View.VISIBLE
            homePageImage.images2.visibility = View.GONE
        }
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
        homepageButtonLayout.epicCard12.cardLeft.setOnClickListener() {
//            recordPosition(0)
            findNavController().navigate(R.id.epic1Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard12.cardRight.setOnClickListener() {
//            recordPosition(1)
            findNavController().navigate(R.id.epic2Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard34.cardLeft.setOnClickListener() {
//            recordPosition(2)
            findNavController().navigate(R.id.epic3Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard34.cardRight.setOnClickListener() {
//            recordPosition(3)
            findNavController().navigate(R.id.epic4Fragment, null, navAnimationLeftToRight())
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_item_two_light -> {
                    if (!animatorSetLight.isRunning && !animatorSetNight.isRunning && !animatorDriving.isRunning) {
                        configTheme("light")

                    }
                    true
                }
                R.id.action_item_two_dark -> {
                    if (!animatorSetLight.isRunning && !animatorSetNight.isRunning && !animatorDriving.isRunning) {
                        configTheme("night")

                    }
                    true
                }
                R.id.action_item_one_dark, R.id.action_item_one_light -> {
                    rainingAnimation()

                    true
                }
                else -> false
            }
        }
    }

    // start all animation
    private fun startAnimation(mode: String) {
        when (mode) {
            "light" -> animatorSetLight.start()
            "night" -> animatorSetNight.start()
        }

        homePageImage.images.setOnClickListener {
//            if (animatorDriving.isRunning) {
//                animatorDriving.cancel()
//                animatorDriving.start()
//            }

            if (!animatorSetLight.isRunning && !animatorSetNight.isRunning) {
                homePageImage.images.visibility = View.INVISIBLE
                homePageImage.images2.visibility = View.VISIBLE
                animatorDriving.start()
            }
        }
//        homePageImage.images2.setOnClickListener{
//            if (animatorDriving.isRunning) {
//                animatorDriving.cancel()
//                animatorDriving.start()
//            }
//        }
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
            .setAnchor(homepageButtonLayout.epicCard12.cardLeft)
            .setShape(
                RoundedRectangle(
                    homepageButtonLayout.epicCard12.cardLeft.height * 1.2.toFloat(),
                    homepageButtonLayout.epicCard12.cardLeft.width * 1.2.toFloat(),
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
            .setAnchor(homepageButtonLayout.epicCard12.cardLeft)
            .setShape(
                RoundedRectangle(
                    homepageButtonLayout.epicCard12.cardRight.height * 1.2.toFloat(),
                    homepageButtonLayout.epicCard12.cardRight.width * 1.2.toFloat(),
                    10f
                )
            )
            .setOverlay(sixth)
            .build()

        targets.add(sixthTarget)


        val height = (homepageButtonLayout.epicCard34.cardRight.height
                - homepageButtonLayout.epicCard34.cardRight.top * 2).toFloat()

        // seventh target
        val seventhRoot = FrameLayout(requireContext())
        val seventh = layoutInflater.inflate(R.layout.layout_target, seventhRoot)
        seventh.findViewById<TextView>(R.id.custom_text).text = getString(R.string.seventh_target)

        val seventhTarget = Target.Builder()
            .setAnchor(
                (binding.root.width / 2).toFloat(),
                (requireActivity().findViewById<View>(R.id.bottom_navigation).top - height * 3)
            )
            .setShape(
                RoundedRectangle(
                    homepageButtonLayout.epicCard34.cardLeft.height * 1.2.toFloat(),
                    homepageButtonLayout.epicCard34.cardLeft.width * 1.2.toFloat(),
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
            .setAnchor(
                (binding.root.width / 2).toFloat(),
                (requireActivity().findViewById<View>(R.id.bottom_navigation).top - height)
            )
            .setShape(
                RoundedRectangle(
                    homepageButtonLayout.epicCard34.cardRight.height * 1.2.toFloat(),
                    homepageButtonLayout.epicCard34.cardRight.width * 1.2.toFloat(),
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

        v0.findViewById<View>(R.id.next_target).setOnClickListener {
            binding.homeScrollView.scrollTo(0, 0)
            spotlight.next()
        }
        first.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        third.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        fourth.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        var scroll =
            homepageButtonLayout.epicCard12.cardLeft.top + homepageButtonLayout.epicCard12.cardLeft.bottom
        fifth.findViewById<View>(R.id.next_target).setOnClickListener {
            binding.homeScrollView.scrollTo(0, scroll)
            spotlight.next()
        }
        sixth.findViewById<View>(R.id.next_target).setOnClickListener {
            binding.homeScrollView.fullScroll(ScrollView.FOCUS_DOWN)
            spotlight.next()
        }
        seventh.findViewById<View>(R.id.next_target).setOnClickListener {
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
                        model.setWeather(weather)
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
                homePageImage.homepageAppBar.setBackgroundResource(R.color.white)
                homePageImage.headlight.visibility = View.INVISIBLE
//                homePageImage.homepageAppBar.setBackgroundResource(R.drawable.bluesky_snow_gradient)
                homePageImage.backpack.alpha = 0f
                homePageImage.backpack.setImageResource(R.drawable.driver_backpack)
                homePageImage.helmet.alpha = 0f
                homePageImage.headlight.alpha = 0f
               // homePageImage.groundForDriver.visibility = View.VISIBLE
                startAnimation("light")
//                setToolbarLightMode(toolbar)
                setToolbarGray(toolbar)
            }
            "night" -> {
//               val gd = GradientDrawable(
//                    GradientDrawable.Orientation.TOP_BOTTOM,
//                intArrayOf(R.color.darkSky, R.color.snow))
//                gd.cornerRadius = 0f

//                homePageImage.homepageAppBar.background = gd
                homePageImage.homepageAppBar.setBackgroundResource(R.drawable.darksky_snow_gradient)
                homePageImage.headlight.visibility = View.VISIBLE
                homePageImage.backpack.alpha = 0f
                homePageImage.backpack.setImageResource(R.drawable.backpack_dark)
                homePageImage.helmet.alpha = 0f
                homePageImage.headlight.alpha = 0f
//                homePageImage.groundForDriver.visibility = View.INVISIBLE
                startAnimation("night")
//                setToolbarDarkMode(toolbar)
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
