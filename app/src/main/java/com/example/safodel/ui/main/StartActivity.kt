package com.example.safodel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.example.safodel.databinding.ActivityStartBinding

import me.jessyan.autosize.AutoSizeConfig
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.view.animation.*
import com.example.safodel.R


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageAnimation()
        buttonAnimation()

        binding.startButton.card.setOnClickListener {
            val intent = Intent()

            // avoid to return to this activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this@StartActivity, MainActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        AutoSizeConfig.getInstance().isBaseOnWidth = false

    }

    private fun imageAnimation() {
        val slideInRight: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)

        slideInRight.interpolator = DecelerateInterpolator()
        slideInRight.duration = 2000

        val animation = AnimationSet(false)
        animation.addAnimation(slideInRight)
        animation.repeatCount = 1;
        binding.image.animation = animation
        binding.image.visibility = View.VISIBLE
    }

    private fun buttonAnimation() {

        val fadeIn: Animation = AlphaAnimation(0.0F, 1.0F)
        fadeIn.interpolator = AccelerateDecelerateInterpolator()
        fadeIn.duration = 3000

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)
        animation.repeatCount = 1;
        binding.startButton.card.animation = animation
        binding.startButton.card.visibility = View.VISIBLE
    }

    private fun subTitleAnimation() {
        val slideInLeft: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        slideInLeft.interpolator = DecelerateInterpolator()
        slideInLeft.duration = 3000

        val animation = AnimationSet(false)
        animation.addAnimation(slideInLeft)
        animation.repeatCount = 1;
        binding.subtitle.animation = animation
    }
}