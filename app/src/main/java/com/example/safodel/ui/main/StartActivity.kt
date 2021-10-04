package com.example.safodel.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.example.safodel.databinding.ActivityStartBinding

import me.jessyan.autosize.AutoSizeConfig
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import android.util.Log
import android.view.animation.*
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.safodel.R
import com.example.safodel.adapter.SafodelViewAdapter
import java.util.*
import android.util.DisplayMetrics
import android.os.Build



class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var viewPage2: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configPrefLanguageFromSharedPref()

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.button.alpha = 0f
        viewPage2 = binding.startViewPager2
        val adapter = SafodelViewAdapter(this)
        binding.startViewPager2.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.startViewPager2)

        configAllAnimations()
        configOnClickListener()


        binding.learningMode.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.empty_12, 0,
            R.drawable.ic_baseline_navigate_next_24_gray, 0
        )

        binding.settingLanguages.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.empty_12, 0,
            R.drawable.ic_baseline_navigate_next_24_gray, 0
        )

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.learningMode.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isLearningMode", true)
            // avoid to return to this activity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this@StartActivity, MainActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.settingLanguages.setOnClickListener {
            switchLanguageList()
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
        var objectAnimator5: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.settingLanguages, "alpha", 0f, 1f)
//        objectAnimator1.duration = 1300
//        objectAnimator2.duration = 1300
        objectAnimator3.duration = 1000
        objectAnimator4.duration = 1000
        objectAnimator5.duration = 1000

        val animatorSet = AnimatorSet()
//        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3)
        animatorSet.play(objectAnimator3).before(objectAnimator4)
        animatorSet.play(objectAnimator4).before(objectAnimator5)
        animatorSet.start()
    }

    private fun switchLanguageList() {
        val listLanguages: Array<String> = arrayOf("English", "हिंदी", "中文")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(getString(R.string.select_language))
        mBuilder.setSingleChoiceItems(listLanguages, -1) { dialog, it ->
            when (it) {
                0 -> {
                    setLocale("en_AU")
                }
                1 -> {
                    setLocale("hi")

                }
                2 -> {
                    setLocale("zh_CN")
                }
//                3 -> {
//                    setLocale("zh_TW")
//                }
            }
            recreateActivity()
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val dm: DisplayMetrics = this.resources.displayMetrics
        val config: Configuration = this.resources.configuration

        when (lang) {
            "en_AU" -> {
                config.locale = Locale.ENGLISH
            }
            "hi" -> {
                config.locale = locale
            }
            "zh_CN" -> {
                config.locale = Locale.CHINESE
            }
//            "zh_TW" -> {
//                config.locale = Locale.TRADITIONAL_CHINESE
//            }
        }

        resources.updateConfiguration(config, dm)
        keepLanguageToSharedPref(lang)
    }

    private fun keepLanguageToSharedPref(lang: String) {
        val spEditor =
            this.applicationContext.getSharedPreferences("language", Activity.MODE_PRIVATE).edit()
        spEditor.putString("lang", lang)
        spEditor.apply()
    }

    private fun configPrefLanguageFromSharedPref() {
        val mSharePreferences =
            this.applicationContext.getSharedPreferences("language", Activity.MODE_PRIVATE)
        val language = mSharePreferences.getString("lang", "")
        if (!language.isNullOrEmpty()) {
            setLocale(language)
        }
    }

    private fun recreateActivity() {
        try {
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.HONEYCOMB) {
            super.recreate()
        } else {
            finish()
            startActivity(intent)
        }
        } catch (e: NullPointerException) {
            Log.d("NullPointerException", e.message.toString())
        }
    }
}

/*
    change language func referred to https://www.youtube.com/watch?v=xxPzi2h0Vvc&ab_channel=ParhoLikhoCS
 */


