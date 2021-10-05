package com.example.safodel.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.ajithvgiri.searchdialog.OnSearchItemSelected
import com.ajithvgiri.searchdialog.SearchListItem
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding
import com.example.safodel.model.UserLocation
import com.example.safodel.model.WeatherTemp
import com.example.safodel.viewModel.HistoryDetailViewModel
import com.example.safodel.viewModel.LocationViewModel
import com.example.safodel.viewModel.TimeEntryWithQuizResultViewModel
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.maps.extension.style.expressions.dsl.generated.length
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig
import timber.log.Timber
import java.util.*
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), View.OnClickListener, OnSearchItemSelected {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var toastMain: Toast
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomMenu: Menu
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

    private val PERMISSION_ID = 1000
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //    lateinit var userLocation: UserLocation
    private val viewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawer = binding.drawerLayout
        bottomMenu = binding.bottomNavigation.menu
        bottomNavigationView = binding.bottomNavigation
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController // Control fragment
        configBottomNavigation() //method to set up bottom nav
        configLeftNavigation() // method to set up left nav
        AutoSizeConfig.getInstance().isBaseOnWidth = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        toastMain = Toast.makeText(this, null, Toast.LENGTH_SHORT)

        val leftHeader = binding.leftNavigation.getHeaderView(0)
        val leftHeaderSaFo = leftHeader.findViewById<View>(R.id.left_header_safo)
        val leftHeaderDel = leftHeader.findViewById<View>(R.id.left_header_del)

        leftHeaderSaFo.isClickable = true
        leftHeaderDel.isClickable = true
        leftHeaderSaFo.setOnClickListener(this)
        leftHeaderDel.setOnClickListener(this)

        configCheckListIcon()

        recordLearningMode()
        setMapLearningMode()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

    }


    /**
     * Press the navigation icon to pop up the navigation window
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawer.openDrawer(GravityCompat.START)
        }
        return true
    }

    /**
     * If the user is in home, school and map pages, he/she needs to click twice
     */
    override fun onBackPressed() {
        /*
            stop users to go back if they are in the following fragment,
            giving the warning message at the same time
         */
        if (navController.currentDestination?.id == R.id.quizPageFragment
        ) {
            MaterialDialog(this).show {
                message(
                    text = "${getString(R.string.check_leave)}" +
                            "\n${getString(R.string.warning_msg)}"
                )
                positiveButton(R.string.no)
                negativeButton(R.string.yes)
                this.negativeButton {
                    removeQuizPageFragment()
                    super.onBackPressed()
                }
            }
            return
        }

        // return back home if current destination is the page on the bottom nav
        if (navController.currentDestination?.id == R.id.mapfragment ||
            navController.currentDestination?.id == R.id.quizFragment ||
            navController.currentDestination?.id == R.id.analysisFragment ||
            navController.currentDestination?.id == R.id.checklistFragment
        ) {
            binding.bottomNavigation.selectedItemId = R.id.navHome
            return
        }

        /*
            remove quiz page fragment from pop stack if the current page is not home fragment
            home fragment have already removed all fragment in the pop stack
         */
        if (navController.currentDestination?.id != R.id.homeFragment) {
            removeQuizPageFragment()
            super.onBackPressed()
            return
        }

        // configure the back button to be pressed twice before the app exits
        if (doubleBackToExitPressedOnce) {
            removeQuizPageFragment()
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        toastMain.setText(getString(R.string.back_to_exit_warning))
        toastMain.show()

        // give user three seconds to leave without re-notification
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                doubleBackToExitPressedOnce = false
            }, 3000
        )
    }

    /**
     * Config the bottom navigation basic settings
     */
    private fun configBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            val menuItem = it
            var isItemSelected = false

            // if user is in the quiz page, provide warning message for the user if he/she wants to leave
            if (navController.currentDestination?.id == R.id.quizPageFragment) {
                MaterialDialog(this).show {
                    message(
                        text = "${getString(R.string.check_leave)}" +
                                "\n${getString(R.string.warning_msg)}"
                    )
                    positiveButton(R.string.no)
                    negativeButton(R.string.yes)
                    this.negativeButton {
                        bottomNav(menuItem)
                        isItemSelected = true
                    }
                    closeDrawer()
                }
                isItemSelected
            } else {
                bottomNav(it)
                true
            }
        }

    }

    /**
     * Define each bottom will direct to which page on the bottom nav
     */
    private fun bottomNav(menuItem: MenuItem) {
        menuItem.isChecked = true
        when (menuItem.itemId) {
            R.id.navHome -> {
                navController.popBackStack(
                    R.id.homeFragment,
                    true
                ) // Previous fragment out of stack
                navController.navigate(R.id.homeFragment)
            }
            R.id.navMap -> {
                if (navController.currentDestination?.id != R.id.navMap) {
                    navController.navigate(R.id.mapfragment)
                }
            }
            R.id.navExam -> {
                if (navController.currentDestination?.id != R.id.navExam) {
                    navController.navigate(R.id.quizFragment)
                }
            }
            R.id.navAnalysis -> {
                if (navController.currentDestination?.id != R.id.navAnalysis) {
                    navController.navigate(R.id.analysisFragment)
                }
            }
            R.id.navCheckList -> {
                if (navController.currentDestination?.id != R.id.navCheckList) {
                    navController.navigate(R.id.checklistFragment)
                }
            }
            else -> {
                navController.popBackStack()
                binding.bottomNavigation.selectedItemId = R.id.navHome
            }
        }
    }

    /**
     * Config the left navigation basic settings
     */
    private fun configLeftNavigation() {
        binding.leftNavigation.setCheckedItem(R.id.left_navigation)
        binding.leftNavigation.setNavigationItemSelectedListener {
            val menuItem = it
            var isItemSelected = false

            // if user is in the quiz page, provide warning message for the user if he/she wants to leave
            if (navController.currentDestination?.id == R.id.quizPageFragment) {
                MaterialDialog(this).show {
                    message(
                        text = "${getString(R.string.check_leave)}" +
                                "\n${getString(R.string.warning_msg)}"
                    )
                    positiveButton(R.string.no)
                    negativeButton(R.string.yes)
                    this.negativeButton {
                        isItemSelected = true
                        leftNav(menuItem)
                        menuItem.isChecked = true
                    }
                    closeDrawer()
                }

                isItemSelected
            } else {
                leftNav(it)
                drawer.closeDrawers() // close the drawer of the left navigation.
                true
            }
        }
    }

    /**
     * Define each bottom will direct to which page on the left nav
     */
    private fun leftNav(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.navAppIntro -> {
                if (navController.currentDestination?.id == R.id.appIntroFragment) {
                    navController.popBackStack()
                }
                navController.navigate(R.id.appIntroFragment)
            }
            R.id.navDeveloper -> {
                if (navController.currentDestination?.id == R.id.developerFragment) {
                    navController.popBackStack()
                }
                navController.navigate(R.id.developerFragment)
            }
            R.id.navLanguage -> switchLanguageList()
        }
    }

    private fun removeQuizPageFragment() {
        val fragmentLabel = navController.previousBackStackEntry?.destination?.label
        if (fragmentLabel == "fragment_exam_page") {
            navController.popBackStack(
                R.id.quizPageFragment,
                false
            )
        }
    }

    fun openDrawer() {
        drawer.openDrawer(GravityCompat.START)
    }

    fun closeDrawer() {
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        return super.getResources()
    }


    /**
     * Control the bottom navigation is visible or not.
     */
    fun isBottomNavigationVisible(boolean: Boolean) {
        if (!boolean)
            binding.bottomNavigation.visibility = View.INVISIBLE
        else
            binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun bottomNavHeight(): Int {
        return binding.bottomNavigation.height
    }


    /**
     * For record user want to conduct learning mode or not
     */
    private fun recordLearningMode() {
        val isLearningMode = intent.getBooleanExtra(
            "isLearningMode",
            false
        )
        intent.removeExtra("isLearningMode")
        setLearningMode(isLearningMode)
    }

    fun getStatusHeight(): Int {
        val rec = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rec)
        return rec.top
    }

    fun lockSwipeDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockSwipeDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun setLockSwipeDrawer(isChecked: Boolean) {
        when (isChecked) {
            true -> drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            false -> drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    fun changeCheckListIcon(isChecked: Boolean) {
        when (isChecked) {
            true -> bottomMenu.findItem(R.id.navCheckList).setIcon(R.drawable.checklist_finish)
            false -> bottomMenu.findItem(R.id.navCheckList).setIcon(R.drawable.checklist_not_finish)
        }

    }

    /**
     * keep the record of the checkbox clicked
     */
    fun keepCheckboxSharePrefer(checkbox_num: Int, isChecked: Boolean) {
        val checkbox = "checkbox$checkbox_num"
        Log.d("keepCheckboxSharePrefer", "  $checkbox: $isChecked")
        val sharedPref = this.applicationContext.getSharedPreferences(
            checkbox, MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putBoolean(checkbox, isChecked)
        spEditor.apply()
    }

    /**
     * get the previous checkbox clicked by the user
     */
    fun getCheckboxSharePrefer(checkbox_num: Int): Boolean {
        val checkbox = "checkbox$checkbox_num"
        val sharedPref = this.applicationContext.getSharedPreferences(
            checkbox,
            Context.MODE_PRIVATE
        )

        Log.d("getCheckboxSharePrefer", "$checkbox: ${sharedPref.getBoolean(checkbox, false)}")
        return sharedPref.getBoolean(checkbox, false)
    }


    /**
     * change the clickList icon on the bottom menu based on whether user has ticked all checkbox on the checkList
     */
    private fun configCheckListIcon() {
        if (getCheckboxSharePrefer(1) && getCheckboxSharePrefer(2)
            && getCheckboxSharePrefer(3) && getCheckboxSharePrefer(4)
            && getCheckboxSharePrefer(5) && getCheckboxSharePrefer(6)
            && getCheckboxSharePrefer(7) && getCheckboxSharePrefer(8)
        ) {
            changeCheckListIcon(true)
        } else {
            changeCheckListIcon(false)
        }
    }

    fun callOnNav(index: Int) {
        bottomNavigationView.menu.getItem(index).isChecked = true
    }

    fun setMapLearningMode() {
        val sf = getPreferences(Context.MODE_PRIVATE)
        val editor = sf.edit()
        editor.putBoolean("mapLeaningMode", true)
        editor.apply()
    }

    /**
     * clean the left menu item is selected before
     */
    fun cleanLeftMenuIsChecked() {
        val numOfItems = binding.leftNavigation.menu.size
        var times = 0
        while (times < numOfItems) {
            binding.leftNavigation.menu.getItem(times).isChecked = false
            times++
        }
    }

    /**
     * display the list of languages for user to select
     * once option is selected the setLocale function will be called
     */
    private fun switchLanguageList() {
        val listLanguages: Array<String> = arrayOf("English", "हिंदी", "中文")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(getString(R.string.select_language))
        mBuilder.setSingleChoiceItems(listLanguages, -1) { dialog, it ->
            when (it) {
                0 -> {
                    setLocale("en_AU")
                }
                1 -> {
                    setLocale("hi")
                }
                2 -> {
                    setLocale("zh_CN")
                }
//                3 -> {
//                    setLocale("zh_TW")
//                }
            }

            if (navController.currentDestination?.id == R.id.quizPageFragment) {
                navController.popBackStack()
            }

            recreateActivity()
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /**
     * set the application language
     */
    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val dm: DisplayMetrics = this.resources.displayMetrics
        val config: Configuration = this.resources.configuration

        when (lang) {
            "en_AU" -> {
                config.locale = Locale.ENGLISH
            }
            "hi" -> {
                config.locale = locale
            }
            "zh_CN" -> {
                config.locale = Locale.CHINESE
            }
//            "zh_TW" -> {
//                config.locale = Locale.TRADITIONAL_CHINESE
//            }
        }

        resources.updateConfiguration(config, dm)
        keepLanguageToSharedPref(lang)
    }

    /**
     * keep the selected new language for app into sharedPreference
     */
    private fun keepLanguageToSharedPref(lang: String) {
        val spEditor =
            this.applicationContext.getSharedPreferences("language", Activity.MODE_PRIVATE).edit()
        spEditor.putString("lang", lang)
        spEditor.apply()
    }

    /**
     * Recreate the activity
     */
    private fun recreateActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.recreate()
        } else {
            finish()
            startActivity(intent)
        }
    }

    /**
     * keep the record of the current weather
     */
    fun keepWeatherSharePrefer(currentWeather: String) {
        val weather = "weather"
        val sharedPref = this.applicationContext.getSharedPreferences(
            weather, Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putString(weather, currentWeather)
        spEditor.apply()
    }

    /**
     * get the weather store in sharedPreference
     */
    fun getWeatherSharePrefer(): String? {
        val weather = "weather"
        val sharedPref = this.applicationContext.getSharedPreferences(
            weather,
            Context.MODE_PRIVATE
        )
        return sharedPref.getString(weather, "Placeholder")
    }

    /**
     * update the menu footer information
     */
    fun updateMenuFooterInfo(weatherObject: WeatherTemp) {
        when (weatherObject.weather) {
            "Clear" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.clear)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_clear)
            }
            "Clouds" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.clouds)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_clouds)
            }
            "Rain" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.rain)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_rain)
            }
            "Thunderstorm" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.thunderstorm)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_rain)
            }
            "Drizzle" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.drizzle)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_rain)
            }
            "Snow" -> {
                binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.snow)
                binding.leftNavFooter.currentWeatherInfo.text = getString(R.string.weather_rain)
            }
            else -> binding.leftNavFooter.currentWeatherIcon.setImageResource(R.drawable.dust)
        }

        binding.leftNavFooter.currentLocationInfo.text = weatherObject.location
        binding.leftNavFooter.currentTempInfo.text = weatherObject.temp.toString() + "°C"
        binding.leftNavFooter.currentHumidityInfo.text = weatherObject.humidity.toString() + "%"
        binding.leftNavFooter.currentWindSpeedInfo.text =
            weatherObject.windSpeed.toString() + " " + getString(R.string.miles_per_hour)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.left_header_safo, R.id.left_header_del -> {
                drawer.closeDrawers()
            }
        }
    }

    /**
     * get sharePref for key = isLearningMode
     */
    fun isLearningMode(): Boolean {
        val sharedPref = this.applicationContext.getSharedPreferences(
            "isLearningMode",
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean("isLearningMode", false)
    }

    /**
     * set sharePref for key = isLearningMode
     */
    fun setLearningMode(isLearningMode: Boolean) {
        val sharedPref = this.applicationContext.getSharedPreferences(
            "isLearningMode",
            Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.clear()
        spEditor.putBoolean("isLearningMode", isLearningMode)
        spEditor.apply()
    }

    /**
     * Check the user's permission
     */
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    /**
     * Get the user's permission
     */
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_ID
        )

    }

    private fun isLocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("onRequestPermissionsResult", "debug purpose")
            } else {
                getLastLocation()
            }
        }
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result as Location
                    if (location == null) {
                        toastMain.setText("Unable to get the current location")
                        toastMain.show()

                    } else {
                        val userLocation = UserLocation(location.latitude.toFloat(), location.longitude.toFloat())
                        viewModel.setUserLocation(userLocation)
                        Log.d("getLastLocation", "location: ${location.latitude}, ${location.longitude}")
                    }
                }
            } else {
                toastMain.setText("Please enable your location service")
                toastMain.show()
            }
        } else {
            requestPermission()
        }
    }

    override fun onClick(position: Int, searchListItem: SearchListItem) {

    }
}
