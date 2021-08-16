package com.example.safodel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu_blue_36)
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController // Control fragment

        configBottomNavigation(navController) //method to set up bottom nav
        configLeftNavigation(navController) // method to set up left nav
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

    private fun configBottomNavigation(navController: NavController) {
        binding.bottomNavigation.setOnItemSelectedListener {
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

    private fun configLeftNavigation(navController: NavController) {
        binding.navView.setCheckedItem(R.id.nav_view)
        binding.navView.setNavigationItemSelectedListener {
            if(!navController.popBackStack(it.itemId, false)){
                navController.popBackStack() // Previous fragment out of stack
                when(it.itemId){
                    R.id.navAppIntro -> navController.navigate(R.id.appIntroFragment)
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
}