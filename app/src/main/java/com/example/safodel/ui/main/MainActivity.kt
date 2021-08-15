package com.example.safodel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.safodel.R
import com.example.safodel.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.outline_menu_black_36)
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController // Control fragment

            binding.navView.setCheckedItem(R.id.nav_view)
            binding.navView.setNavigationItemSelectedListener {
                if(!navController.popBackStack(it.itemId, false)){
                    navController.popBackStack() // Previous fragment out of stack
                    when(it.itemId){
                        R.id.navHome -> navController.navigate(R.id.homefragment)
                        R.id.navSchool -> if(navController.currentDestination?.id != it.itemId ) navController.navigate(R.id.schoolFragment)
                    }
                }
                binding.drawerLayout.closeDrawers()
                true
            }

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
}