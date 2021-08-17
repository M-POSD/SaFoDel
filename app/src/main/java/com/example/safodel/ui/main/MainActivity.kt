package com.example.safodel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding
import android.view.Gravity

import android.view.View

import androidx.drawerlayout.widget.DrawerLayout





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController : NavController
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController // Control fragment
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.let {
//            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.menu_blue_36)
//        }
        configBottomNavigation() //method to set up bottom nav
        configLeftNavigation() // method to set up left nav
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
                }
            }
            binding.drawerLayout.closeDrawers() // close the drawer of the left navigation.
            true
        }
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
}