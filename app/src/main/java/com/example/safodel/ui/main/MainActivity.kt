package com.example.safodel.ui.main

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding

import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var toastMain: Toast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
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
            binding.drawerLayout.closeDrawers()
        }

        recordLearningMode()
    }



    /**
     * Press the navigation icon to pop up the navigation window
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    /**
     * If the user is in home, school and map pages, he/she needs to click twice
     */
    override fun onBackPressed() {
        // stop users to go back if they are in the following fragment,
        // giving the warning message at the same time
        if (navController.currentDestination?.id == R.id.exam1Fragment ||
            navController.currentDestination?.id == R.id.examFinishFragment) {
            toastMain.setText("Not allow go back in the page")
            toastMain.show()
            return
        }

        if(navController.currentDestination?.id == R.id.mapfragment ||
            navController.currentDestination?.id == R.id.examFragment ||
            navController.currentDestination?.id == R.id.analysisFragment){
            binding.bottomNavigation.selectedItemId = R.id.navHome
            return
        }

        if(navController.currentDestination?.id != R.id.homeFragment)
        {
            super.onBackPressed()
            return
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        // give user three seconds to leave without re-notification
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 3000)
    }

    private fun configBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    navController.popBackStack(R.id.homeFragment, true) // Previous fragment out of stack
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.navMap -> {
                    if(navController.currentDestination?.id != R.id.navMap){
                        navController.navigate(R.id.mapfragment)
                    }
                    true
                }
                R.id.navExam -> {
                    if(navController.currentDestination?.id != R.id.navExam){
                        navController.navigate(R.id.examFragment)
                    }
                    true
                }
                R.id.navAnalysis -> {
                    if(navController.currentDestination?.id != R.id.navAnalysis){
                        navController.navigate(R.id.analysisFragment)
                    }
                    true
                }
                else -> {
                    navController.popBackStack()
                    binding.bottomNavigation.selectedItemId = R.id.navHome
                    true
                }
            }
        }

    }

    private fun configLeftNavigation() {
        binding.leftNavigation.setCheckedItem(R.id.left_navigation)
        binding.leftNavigation.setNavigationItemSelectedListener {
            if (!navController.popBackStack(it.itemId, false)) {
                if (navController.currentDestination?.id == R.id.appIntroFragment)
                    navController.popBackStack() // Previous fragment out of stack
                when (it.itemId) {
                    R.id.navAppIntro -> navController.navigate(R.id.appIntroFragment)
                    R.id.navDeveloper -> navController.navigate(R.id.developerFragment)
//                    R.id.navExam -> navController.navigate(R.id.examFragment)
//                    R.id.navAnalysis -> navController.navigate(R.id.analysisFragment)
                }
            }
            binding.drawerLayout.closeDrawers() // close the drawer of the left navigation.
            true
        }
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
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

    fun bottomNavHeight(): Int{
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
}


//
// val currentFragment = supportFragmentManager.fragments.last().childFragmentManager.fragments.last()
//var action = navController.currentDestination
//                    val currentFragment = navController.currentDestination?.removeAction()
//
//Log.e("Fragment", action.toString())
