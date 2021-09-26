package com.example.safodel.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding
import com.example.safodel.viewModel.HistoryDetailViewModel
import com.example.safodel.viewModel.TimeEntryWithQuizResultViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.maps.extension.style.expressions.dsl.generated.length

import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var toastMain: Toast
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomMenu: Menu
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment

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
        val leftHeaderText = leftHeader.findViewById<View>(R.id.left_header_text)
        leftHeaderText.isClickable = true
        leftHeaderText.setOnClickListener {
            drawer.closeDrawers()
        }

        configCheckListIcon()

        recordLearningMode()
        setMapLearningMode()

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
        // stop users to go back if they are in the following fragment,
        // giving the warning message at the same time
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

        if (navController.currentDestination?.id == R.id.mapfragment ||
            navController.currentDestination?.id == R.id.quizFragment ||
            navController.currentDestination?.id == R.id.analysisFragment ||
            navController.currentDestination?.id == R.id.checklistFragment
        ) {
            binding.bottomNavigation.selectedItemId = R.id.navHome
            return
        }

        if (navController.currentDestination?.id != R.id.homeFragment) {
            removeQuizPageFragment()
            super.onBackPressed()
            return
        }

        if (doubleBackToExitPressedOnce) {
            removeQuizPageFragment()
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        toastMain.cancel()
        toastMain.setText("Please click BACK again to exit")
        toastMain.show()

        // give user three seconds to leave without re-notification
        Handler(Looper.getMainLooper()).postDelayed(Runnable
        {
            doubleBackToExitPressedOnce = false
        }, 3000
        )
    }

    private fun configBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            val menuItem = it
            var isItemSelected = false
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
                }
                isItemSelected
            } else {
                bottomNav(it)
                true
            }
        }

    }

    private fun bottomNav(item: MenuItem) {
        when (item.itemId) {
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

    private fun configLeftNavigation() {
        binding.leftNavigation.setCheckedItem(R.id.left_navigation)
        binding.leftNavigation.setNavigationItemSelectedListener {
            val menuItem = it
            var isItemSelected = false
            if (navController.currentDestination?.id == R.id.quizPageFragment) {
                MaterialDialog(this).show {
                    message(
                        text = "${getString(R.string.check_leave)}" +
                                "\n${getString(R.string.warning_msg)}"
                    )
                    positiveButton(R.string.no)
                    negativeButton(R.string.yes)
                    this.negativeButton {
                        when (menuItem.itemId) {
                            R.id.navAppIntro -> navController.navigate(R.id.appIntroFragment)
                            R.id.navDeveloper -> navController.navigate(R.id.developerFragment)
                        }
                        isItemSelected = true
                    }
                }

                isItemSelected
            } else {
                if (!navController.popBackStack(it.itemId, false)) {
                    if (navController.currentDestination?.id == R.id.appIntroFragment
                        || navController.currentDestination?.id == R.id.developerFragment
                    ) {
                        navController.popBackStack() // Previous fragment out of stack
                    }
                    when (it.itemId) {
                        R.id.navAppIntro -> navController.navigate(R.id.appIntroFragment)
                        R.id.navDeveloper -> navController.navigate(R.id.developerFragment)
                    }
                }
                drawer.closeDrawers() // close the drawer of the left navigation.
                true
            }
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
        val sharedPref = this.applicationContext.getSharedPreferences(
            "isLearningMode", Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putBoolean("isLearningMode", isLearningMode)
        spEditor.apply()
    }

    fun getStatusHeight(): Int {
        val rec = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rec)
        val statusBarHeight = rec.top
        return statusBarHeight
    }

    fun lockSwipeDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockSwipeDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun changeCheckListIcon(isChecked: Boolean) {
        when (isChecked) {
            true -> bottomMenu.findItem(R.id.navCheckList).setIcon(R.drawable.checklist_finish)
            false -> bottomMenu.findItem(R.id.navCheckList).setIcon(R.drawable.checklist_not_finish)
        }

    }

    // keep the record of the checkbox clicked
    fun keepCheckboxSharePrefer(checkbox_num: Int, isChecked: Boolean) {
        val checkbox = "checkbox$checkbox_num"
        val sharedPref = this.applicationContext.getSharedPreferences(
            checkbox, Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putBoolean(checkbox, isChecked)
        spEditor.apply()
    }

    // get the previous checkbox clicked by the user
    fun getCheckboxSharePrefer(checkbox_num: Int): Boolean {
        val checkbox = "checkbox$checkbox_num"
        val sharedPref = this.applicationContext.getSharedPreferences(
            checkbox,
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(checkbox, false)
    }

    // keep the record of the checkbox clicked
    fun keepWeatherSharePrefer(currentWeather: String) {
        val weather = "weather"
        val sharedPref = this.applicationContext.getSharedPreferences(
            weather, Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putString(weather, currentWeather)
        spEditor.apply()
    }

    // get the previous checkbox clicked by the user
    fun getWeatherSharePrefer(): String? {
        val weather = "weather"
        val sharedPref = this.applicationContext.getSharedPreferences(
            weather,
            Context.MODE_PRIVATE
        )
        return sharedPref.getString(weather, "Placeholder")
    }

    private fun configCheckListIcon() {
        if (getCheckboxSharePrefer(1) && getCheckboxSharePrefer(2)
            && getCheckboxSharePrefer(3) && getCheckboxSharePrefer(4)
            && getCheckboxSharePrefer(5) && getCheckboxSharePrefer(6)
            && getCheckboxSharePrefer(6)
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
}


//
// val currentFragment = supportFragmentManager.fragments.last().childFragmentManager.fragments.last()
//var action = navController.currentDestination
//                    val currentFragment = navController.currentDestination?.removeAction()
//
//Log.e("Fragment", action.toString())
