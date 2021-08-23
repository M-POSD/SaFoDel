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
import com.example.safodel.databinding.ActivityStartBinding

import com.google.android.material.tabs.TabLayout
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.AutoSizeConfig


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AutoSizeConfig.getInstance().isBaseOnWidth = false

    }
}