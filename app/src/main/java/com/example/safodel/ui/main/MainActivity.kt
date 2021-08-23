package com.example.safodel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.safodel.databinding.ActivityStartBinding

import me.jessyan.autosize.AutoSizeConfig


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AutoSizeConfig.getInstance().isBaseOnWidth = false

    }
}
