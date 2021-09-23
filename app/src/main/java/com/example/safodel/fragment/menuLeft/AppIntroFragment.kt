package com.example.safodel.fragment.menuLeft

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.safodel.R
import com.example.safodel.databinding.FragmentAppIntroBinding
import com.example.safodel.fragment.BasicFragment


class AppIntroFragment : BasicFragment<FragmentAppIntroBinding>(FragmentAppIntroBinding::inflate){
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppIntroBinding.inflate(inflater,container,false)

        val toolbar = binding.toolbar.root

        binding.about.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.about.setOnClickListener {
            MaterialDialog(requireContext()).show{
                customView(R.layout.appinfo_about)
            }
        }

        binding.audience.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.audience.setOnClickListener {
            MaterialDialog(requireContext()).show {
                customView(R.layout.appinfo_audience)
            }
        }
//        configHelmetShaking()

        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun configHelmetShaking() {
//        handler = Handler(Looper.getMainLooper())
//        runnable = Runnable {
//            binding.logo.visibility = View.VISIBLE
//            binding.logoShaking.visibility = View.GONE
//    }
//        binding.logo.setOnClickListener{
//            binding.logo.visibility = View.GONE
//            binding.logoShaking.visibility = View.VISIBLE
//            handler.postDelayed(runnable, 1000)
//        }
//
//    }

}