package com.example.safodel.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.example.safodel.databinding.ActivityStartBinding

import me.jessyan.autosize.AutoSizeConfig
import android.content.Intent
import android.view.animation.*
import androidx.viewpager2.widget.ViewPager2
import com.example.safodel.R
import com.example.safodel.adapter.SafodelViewAdapter


class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var viewPage2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startButton.button.alpha = 0f

        viewPage2 = binding.startViewPager2
        val adapter = SafodelViewAdapter(this)
        binding.startViewPager2.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.startViewPager2)

        configAllAnimations()
        configOnClickListener()


        binding.learningMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.empty_12,0,
            R.drawable.baseline_chevron_right_green_12,0)

        AutoSizeConfig.getInstance().isBaseOnWidth = false

    }

    private fun configOnClickListener() {
        binding.startButton.button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isLearningMode", false)
            // avoid to return to this activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this@StartActivity, MainActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        binding.learningMode.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isLearningMode", true)
            // avoid to return to this activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this@StartActivity, MainActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    // confi all animations in the start activity
    private fun configAllAnimations() {
//        var objectAnimator1: ObjectAnimator =
//            ObjectAnimator.ofFloat(binding.image, "translationX", 100f, 0f)
//        var objectAnimator2: ObjectAnimator = ObjectAnimator.ofFloat(binding.image, "alpha", 0f, 1f)
        var objectAnimator3: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.startButton.button, "alpha", 0f, 1f)
        var objectAnimator4: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.learningMode, "alpha", 0f, 1f)
//        objectAnimator1.duration = 1300
//        objectAnimator2.duration = 1300
        objectAnimator3.duration = 1300
        objectAnimator4.duration = 1300

        val animatorSet = AnimatorSet()
//        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3)
        animatorSet.play(objectAnimator3).before(objectAnimator4)
        animatorSet.start()
    }

}

