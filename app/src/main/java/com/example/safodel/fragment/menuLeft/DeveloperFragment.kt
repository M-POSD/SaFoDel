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
import com.example.safodel.databinding.FragmentDeveloperBinding
import com.example.safodel.fragment.BasicFragment


class DeveloperFragment : BasicFragment<FragmentDeveloperBinding>(FragmentDeveloperBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeveloperBinding.inflate(inflater,container,false)

        binding.knowMore.paintFlags  = Paint.UNDERLINE_TEXT_FLAG

        // connect to TA27 Mahara page
        binding.knowMore.setOnClickListener {
            val url = "https://bit.ly/ta27-safodel"
            val internetAct = Intent(Intent.ACTION_VIEW)
            internetAct.data = Uri.parse(url)
            startActivity(internetAct)
        }

        binding.about.paintFlags  = Paint.UNDERLINE_TEXT_FLAG
        binding.about.setOnClickListener {
            MaterialDialog(requireContext()).show{
                title(text = getString(R.string.about))
                message(text = getString(R.string.about_us))
            }
        }

        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}