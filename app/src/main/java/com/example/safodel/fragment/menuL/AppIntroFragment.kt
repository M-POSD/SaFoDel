package com.example.safodel.fragment.menuL

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.safodel.R
import com.example.safodel.databinding.FragmentAppIntroBinding
import com.example.safodel.fragment.BasicFragment


class AppIntroFragment : BasicFragment<FragmentAppIntroBinding>(FragmentAppIntroBinding::inflate){
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




        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}