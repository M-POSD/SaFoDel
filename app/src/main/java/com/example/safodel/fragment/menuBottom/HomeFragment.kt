package com.example.safodel.fragment.menuBottom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import kotlin.collections.ArrayList
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.safodel.adapter.HomeViewAdapter
import com.example.safodel.databinding.HomepageButtonLayoutBinding
import com.example.safodel.databinding.HomepageImagesBinding
import com.example.safodel.model.WeatherTemp
import com.example.safodel.viewModel.IsLearningModeViewModel
import com.example.safodel.viewModel.LocationViewModel
import com.example.safodel.viewModel.WeatherViewModel
import com.google.android.material.appbar.AppBarLayout
import com.mapbox.android.core.permissions.PermissionsListener
import kotlinx.coroutines.*
import pl.droidsonroids.gif.GifImageView
import java.lang.Runnable
import kotlin.math.abs
import kotlin.math.roundToInt


class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    PermissionsListener {
    // get weather
    private val appId = "898ef19b846722554449f6068e7c7253"
    private val units = "metric"
    private var lat = -37.876823f
    private var lon = 145.045837f

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
    private lateinit var homepageButtonLayout2: HomepageButtonLayoutBinding
    private lateinit var homeScrollView: NestedScrollView

    private var isBeginnerMode = false
    private var isFirstCreated = true
    private var currentToast: Toast? = null

    private lateinit var adapter: HomeViewAdapter
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    private lateinit var weatherModel: WeatherViewModel
    private lateinit var learningModeModel: IsLearningModeViewModel
    private val locationViewModel: LocationViewModel by activityViewModels()


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

        handler = Handler(Looper.getMainLooper())
        weatherService = RetrofitClient.getRetrofitService()

        toast = Toast.makeText(requireActivity(), null, Toast.LENGTH_SHORT)
        toolbar = binding.toolbar.root
        homePageImage = binding.homePageImages
        homepageButtonLayout = binding.homepageButtonLayout
        homepageButtonLayout2 = binding.homepageButtonLayout2

        homeScrollView = binding.homeScrollView
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
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {

            async {
                // try to get the height of status bar and then margin top
                val toolbarHeight = toolbar.layoutParams as CoordinatorLayout.LayoutParams
                while (toolbarHeight.topMargin == 0)
                    toolbarHeight.topMargin = mainActivity.getStatusHeight()
                toolbar.layoutParams = toolbarHeight
            }.await()

            withContext(Dispatchers.Main){

            }
        }

        binding.toolbar.simpleToolbar.fitsSystemWindows = false


        adapter = HomeViewAdapter(requireActivity(), this)
        homepageButtonLayout.viewPager2Home.adapter = adapter
        homepageButtonLayout.wormDotsIndicatorHome.setViewPager2(homepageButtonLayout.viewPager2Home)

        isBeginnerMode = mainActivity.isLearningMode()

        val viewModelProvider = ViewModelProvider(requireActivity())
        weatherModel = viewModelProvider.get(WeatherViewModel::class.java)
        learningModeModel = viewModelProvider.get(IsLearningModeViewModel::class.java)

        locationViewModel.getUserLocation().observe(mainActivity, { location ->
            lat = location.lat
            lon = location.lon
            callWeatherService()
        })

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.doOnPreDraw {
            if (isBeginnerMode) {
                learningModeModel.setLearningMode(true)
                startSpotLight()
            } else {
                learningModeModel.setLearningMode(false)
            }
            isBeginnerMode = false
            isFirstCreated = false
        }
    }

    override fun onStart() {
        super.onStart()
        setViewPager2AutoIncrementPosition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViewPager2AutoIncrementPosition() {
        if (isFirstCreated) {
            runnable = Runnable {
                if (homepageButtonLayout.viewPager2Home.currentItem == 4) {
                    homepageButtonLayout.viewPager2Home.currentItem -= 4
                } else {
                    homepageButtonLayout.viewPager2Home.currentItem += 1
                }
                handler.postDelayed(runnable, 6000) //5 sec delay
            }

            handler.postDelayed(runnable, 6000)
        }
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
        homepageButtonLayout.epicCard12.scImageViewLeft.setImageResource(R.drawable.tipv2)
        homepageButtonLayout.epicCard12.scImageViewRight.setImageResource(R.drawable.delivery_on_ebike2)
        homepageButtonLayout.epicCard34.scImageViewLeft.setImageResource(R.drawable.road_sign2)
        homepageButtonLayout.epicCard34.scImageViewRight.setImageResource(R.drawable.in_an_accident2)
    }

    // raining animation
    private fun rainingAnimation() {
        homePageImage.vusik.setImages(rainingList).start()
        homePageImage.vusik.startNotesFall()
    }

    // add animation for the individual image
    private fun imageAnimations() {
        val objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.backpack,
                "translationX",
                -100f,
                homePageImage.backpack.translationX
            )
        val objectAnimator2: ObjectAnimator =
            ObjectAnimator.ofFloat(homePageImage.backpack, "alpha", 0f, 1f)
        val objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.helmet,
                "translationY",
                -120f,
                homePageImage.helmet.translationY
            )

        val objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(homePageImage.helmet, "alpha", 0f, 1f)
        val objectAnimator5: ObjectAnimator =
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
        val objectAnimator1: ObjectAnimator =
            ObjectAnimator.ofFloat(
                homePageImage.images2,
                "translationX",
                0f,
                4 * (view?.width ?: 1500) / 5.toFloat()
            )

        val objectAnimator2: ObjectAnimator =
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

    // config onClickListener for navigation
    private fun configOnClickListener() {
        homepageButtonLayout.epicCard12.cardLeft.setOnClickListener {
            setViewPagerPosition(homepageButtonLayout.viewPager2Home.currentItem)
            findNavController().navigate(R.id.epic1Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard12.cardRight.setOnClickListener {
            setViewPagerPosition(homepageButtonLayout.viewPager2Home.currentItem)
            findNavController().navigate(R.id.epic2Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard34.cardLeft.setOnClickListener {
            setViewPagerPosition(homepageButtonLayout.viewPager2Home.currentItem)
            findNavController().navigate(R.id.epic3Fragment, null, navAnimationLeftToRight())
        }

        homepageButtonLayout.epicCard34.cardRight.setOnClickListener {
            setViewPagerPosition(homepageButtonLayout.viewPager2Home.currentItem)
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
    private fun startAnimation() {
        animatorSetLight.start()
        homePageImage.images.setOnClickListener {
            if (!animatorSetLight.isRunning && !animatorSetNight.isRunning) {
                homePageImage.images.visibility = View.INVISIBLE
                homePageImage.images2.visibility = View.VISIBLE
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
        val bottomNavigation = requireActivity().findViewById<View>(R.id.bottom_navigation)
        first.findViewById<TextView>(R.id.custom_text).text = getString(R.string.first_target)
        val firstTarget = Target.Builder()
            .setAnchor(bottomNavigation)
            .setShape(
                RoundedRectangle(
                    bottomNavigation.height.toFloat(),
                    bottomNavigation.width.toFloat(),
                    10f
                )
            )
            .setOverlay(first)
            .build()
        targets.add(firstTarget)

        // second target
        val secondRoot = FrameLayout(requireContext())
        val second = layoutInflater.inflate(R.layout.layout_target, secondRoot)
        second.findViewById<TextView>(R.id.custom_text).text = getString(R.string.second_target)
//        val scrollingView = homepageButtonLayout2.viewPager2Home
        val scrollingView = requireActivity().findViewById<View>(R.id.view_pager2_home)
        val secondTarget = Target.Builder()
            .setAnchor(scrollingView)
            .setShape(
                RoundedRectangle(
                    scrollingView.height.toFloat(),
                    scrollingView.width.toFloat(),
                    10f
                )
            )
            .setOverlay(second)
            .build()
        targets.add(secondTarget)

        // third target
        val thirdRoot = FrameLayout(requireContext())
        val third = layoutInflater.inflate(R.layout.layout_target, thirdRoot)
        third.findViewById<TextView>(R.id.custom_text).text = getString(R.string.third_target)
        third.findViewById<GifImageView>(R.id.scrolling_gif).visibility = View.VISIBLE
//        val scrollingView = homepageButtonLayout2.viewPager2Home
        val thirdTarget = Target.Builder()
            .setShape(Circle(0f))
            .setOverlay(third)
            .build()
        third.findViewById<TextView>(R.id.next_target).alpha = 0f
        targets.add(thirdTarget)

        // fourth target
        val fourthRoot = FrameLayout(requireContext())
        val fourth = layoutInflater.inflate(R.layout.layout_target, fourthRoot)
        fourth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.fourth_target)
        val staticInfoBtnView = homepageButtonLayout2.staticInfoLayout
        val fourthTarget = Target.Builder()
            .setAnchor(staticInfoBtnView)
            .setShape(
                RoundedRectangle(
                    staticInfoBtnView.height.toFloat(),
                    staticInfoBtnView.width.toFloat(),
                    10f
                )
            )
            .setOverlay(fourth)
            .build()
        targets.add(fourthTarget)

        // fifth target
        val fifthRoot = FrameLayout(requireContext())
        val fifth = layoutInflater.inflate(R.layout.layout_target, fifthRoot)
        fifth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.fifth_target)
        fifth.findViewById<GifImageView>(R.id.scrolling_gif).setImageResource(R.drawable.scrolling_down)
        fifth.findViewById<GifImageView>(R.id.scrolling_gif).visibility = View.VISIBLE
        val fifthTarget = Target.Builder()
            .setShape(Circle(0f))
            .setOverlay(fifth)
            .build()
        fifth.findViewById<TextView>(R.id.next_target).alpha = 0f
        targets.add(fifthTarget)

        // ninth target
        val ninthRoot = FrameLayout(requireContext())
        val ninth = layoutInflater.inflate(R.layout.layout_target, ninthRoot)
        ninth.findViewById<TextView>(R.id.custom_text).text = getString(R.string.ninth_target)

//        val location = binding.toolbar.simpleToolbar.getChildAt(1).getLocationOnScreen()
//        val absX = location.x
//        val absY = location.y + mainActivity.getStatusHeight()

        val toolbarHeight = toolbar.layoutParams as CoordinatorLayout.LayoutParams
        while (toolbarHeight.topMargin == 0)
            toolbarHeight.topMargin = mainActivity.getStatusHeight()
        toolbar.layoutParams = toolbarHeight

         val  ninthTarget = Target.Builder()
            .setAnchor(binding.toolbar.simpleToolbar.getChildAt(1))
            .setShape(Circle(120f))
            .setOverlay(ninth)
            .build()
        ninth.findViewById<TextView>(R.id.next_target).alpha = 0f
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

        spotlight.start()

        val nextTarget = View.OnClickListener {
            spotlight.next()
        }

        val closeSpotlight = View.OnClickListener {
            binding.homeScrollView.fullScroll(ScrollView.FOCUS_UP)
            spotlight.finish()
            isAllEnable(true)
            mainActivity.setLearningMode(false)
            learningModeModel.setLearningMode(false)
        }

        v0.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        first.findViewById<View>(R.id.next_target).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.next_target).setOnClickListener{
            spotlight.next()
            configScrollingViewActionCapture(3, third, spotlight)
        }
        fourth.findViewById<View>(R.id.next_target).setOnClickListener{
            spotlight.next()
            configScrollingViewActionCapture(5, fifth, spotlight)
        }

        isAllEnable(false)

        v0.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        second.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        third.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fourth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        fifth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)
        ninth.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)

    }

    // referred from https://stackoverflow.com/questions/6238881/how-to-disable-all-click-events-of-a-layout
    // -> disable the view and its view group
    private fun View.setAllEnabled(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
    }

    private fun isAllEnable(isEnable: Boolean) {
        requireActivity().findViewById<View>(R.id.homeCoordinatorLayout1).setAllEnabled(isEnable)
        homePageImage.homepageAppBar.setAllEnabled(isEnable)
        homepageButtonLayout.viewPager2Home.isUserInputEnabled = isEnable
        mainActivity.setLockSwipeDrawer(isEnable)
        requireActivity().findViewById<View>(R.id.navHome).setAllEnabled(isEnable)
        requireActivity().findViewById<View>(R.id.navMap).setAllEnabled(isEnable)
        requireActivity().findViewById<View>(R.id.navExam).setAllEnabled(isEnable)
        requireActivity().findViewById<View>(R.id.navAnalysis).setAllEnabled(isEnable)
    }

    private fun callWeatherService() {
        val callAsync: Call<WeatherResponse> = weatherService.getCurrentWeatherData(
            lat.toString(),
            lon.toString(),
            appId,
            units
        )

        callAsync.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(
                call: Call<WeatherResponse?>?,
                response: Response<WeatherResponse?>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()!!
                    val weatherList = weatherResponse.weatherMainList

                    lat = (lat * 100.0).roundToInt() / 100.0f
                    lon = (lon * 100.0).roundToInt() / 100.0f
                    val location = "$lat, $lon"
                    val temp = weatherResponse.main.temp
                    val pressure = weatherResponse.main.pressure
                    val humidity = weatherResponse.main.humidity
                    var windSpeed = weatherResponse.wind.speed
                    windSpeed = (windSpeed * 3.6 * 100.0).roundToInt() / 100.0f

                    val weather = weatherList[0].main
                    if (weather == "Rain") {
                        rainingAnimation()
                    }
                    mainActivity.keepWeatherSharePrefer(weather)
                    weatherModel.setWeather(
                        WeatherTemp(
                            location,
                            weather,
                            temp,
                            pressure,
                            humidity,
                            windSpeed
                        )
                    )
                }
            }

            override fun onFailure(call: Call<WeatherResponse?>?, t: Throwable) {
                Toast.makeText(activity, getString(R.string.weather_null), Toast.LENGTH_SHORT).show()
            }
        })
    }
    // theme based on light or night
    private fun configTheme(mode: String) {
        when (mode) {
            "light" -> {
                homePageImage.homepageAppBar.setBackgroundResource(R.color.white)
                homePageImage.headlight.visibility = View.INVISIBLE
                homePageImage.backpack.alpha = 0f
                homePageImage.backpack.setImageResource(R.drawable.driver_backpack_home)
                homePageImage.helmet.alpha = 0f
                homePageImage.headlight.alpha = 0f
                startAnimation()
                setToolbarBasic(toolbar)
            }
        }
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(context, getString(R.string.need_premission), Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            Toast.makeText(context, "Granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, getString(R.string.location_granted), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * keep the record of the checkbox clicked
     */
    private fun setViewPagerPosition(position: Int) {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "position", AppCompatActivity.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putInt("position", position)
        spEditor.apply()
    }

    @SuppressLint("CutPasteId")
    private fun configScrollingViewActionCapture(numTarget:Int, view: View, spotLight: Spotlight) {
        when(numTarget) {
            3 -> {
                homePageImage.homepageAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    when (appBarLayout.totalScrollRange) {
                        //  State Collapsed
                        abs(verticalOffset) -> {
                            view.findViewById<TextView>(R.id.next_target).alpha = 1f
                            view.findViewById<TextView>(R.id.custom_text).text = getString(R.string.click_next)
                            view.findViewById<View>(R.id.next_target).setOnClickListener{
                                spotLight.next()
                            }
                        }
                        else -> {
                            view.findViewById<TextView>(R.id.next_target).alpha = 0f
                            view.findViewById<View>(R.id.next_target).isClickable = false
                            view.findViewById<TextView>(R.id.custom_text).text = getString(R.string.third_target)
                        }
                    }
                })
            }

            5 -> {
//                homePageImage.homepageAppBar.setAllEnabled(true)
                homePageImage.homepageAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    when {
                        //  State Expanded
                        verticalOffset == 0 -> {
                            view.findViewById<TextView>(R.id.next_target).alpha = 1f
                            view.findViewById<TextView>(R.id.custom_text).text = getString(R.string.click_next)
                            view.findViewById<View>(R.id.next_target).setOnClickListener{
                                spotLight.next()
                            }
                        }

                        //  State Collapsed
                        abs(verticalOffset) == appBarLayout.totalScrollRange -> {
                        }

                        else -> {
                            view.findViewById<TextView>(R.id.next_target).alpha = 0f
                            view.findViewById<View>(R.id.next_target).isClickable = false
                            view.findViewById<TextView>(R.id.custom_text).text = getString(R.string.fifth_target)
                        }
                    }
                })
            }
        }

    }

}
