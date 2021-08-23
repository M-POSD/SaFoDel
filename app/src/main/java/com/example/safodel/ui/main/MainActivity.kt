package com.example.safodel.ui.main

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.safodel.R
import com.example.safodel.adapter.EpicViewAdapter
import com.example.safodel.databinding.ActivityMainBinding

import com.google.android.material.tabs.TabLayout
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController // Control fragment
        configBottomNavigation() //method to set up bottom nav
        configLeftNavigation() // method to set up left nav
        AutoSizeConfig.getInstance().isBaseOnWidth = false

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
        if (doubleBackToExitPressedOnce || (navController.currentDestination?.id != R.id.homeFragment
                    && navController.currentDestination?.id != R.id.mapfragment
                    && navController.currentDestination?.id != R.id.schoolFragment)) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        // give user three seconds to leave without re-notification
        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 3000)
    }

    private fun configBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            navController.popBackStack() // Previous fragment out of stack
            when(it.itemId){
                R.id.navHome -> {
                   // val currentFragment = supportFragmentManager.fragments.last().childFragmentManager.fragments.last()
                    var action = navController.currentDestination
//                    val currentFragment = navController.currentDestination?.removeAction()

                    Log.e("Fragment", action.toString())

                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.navSchool -> {
                    navController.navigate(R.id.schoolFragment)
                    true
                }
                R.id.navMap ->{
                    navController.navigate(R.id.mapfragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun configLeftNavigation() {
        binding.leftNavigation.setCheckedItem(R.id.left_navigation)
        binding.leftNavigation.setNavigationItemSelectedListener {
            if(!navController.popBackStack(it.itemId, false)){
                if(navController.currentDestination?.id == R.id.appIntroFragment)
                    navController.popBackStack() // Previous fragment out of stack
                when(it.itemId){
                    R.id.navAppIntro -> navController.navigate(R.id.appIntroFragment)
                    R.id.navDeveloper -> navController.navigate(R.id.developerFragment)
                    // for control the action from Home to AppIntro
//                    R.id.navAppIntro -> {
//                        val action = HomeFragmentDirections.actionHomeFragmentToAppIntroFragment()
//                        navController.navigate(action)
//                    }
//
//                    R.id.navDeveloper -> {
//                        val action = HomeFragmentDirections.actionHomeFragmentToDeveloperFragment()
//                        navController.navigate(action)
//                    }
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
    fun isBottomNavigationVisible(boolean: Boolean){
        if(!boolean)
            binding.bottomNavigation.visibility = View.INVISIBLE
        else
            binding.bottomNavigation.visibility = View.VISIBLE
    }
}

//val animationOptions = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
//    .setExitAnim(R.anim.slide_out_left)
//    .setPopEnterAnim(R.anim.slide_in_left)
//    .setPopExitAnim(R.anim.slide_out_right).build()
