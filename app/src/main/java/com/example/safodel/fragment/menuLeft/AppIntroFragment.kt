package com.example.safodel.fragment.menuLeft

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
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

        binding.what.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.what.setOnClickListener {
            MaterialDialog(requireContext()).show{
                customView(R.layout.appinfo_what)
            }
        }

        binding.why.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.why.setOnClickListener {
            MaterialDialog(requireContext()).show {
                customView(R.layout.appinfo_why)
            }
        }

        binding.how.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.how.setOnClickListener {
            MaterialDialog(requireContext()).show {
                customView(R.layout.appinfo_how)
            }
        }

        // connect to the product video page
        binding.appIntroKnowMore.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.appIntroKnowMore.setOnClickListener {
            val url = "https://www.youtube.com/watch?v=ZcUVtaflDy8"
            val internetAct = Intent(Intent.ACTION_VIEW)
            internetAct.data = Uri.parse(url)
            startActivity(internetAct)
        }


        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}