package com.example.safodel.fragment.menuL

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentExamBinding
import com.example.safodel.fragment.BasicFragment


class ExamFragment : BasicFragment<FragmentExamBinding>(FragmentExamBinding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar.root
        setToolbarWhite(toolbar)

        binding.startBtn.setOnClickListener {
            var userName = binding.editText.text.toString()
            Log.d("userName", userName)
            if (userName.isEmpty()) {
                Toast.makeText(activity, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                var arg = bundleOf(
                    Pair("userName", userName)
                )
                findNavController().navigate(R.id.exam1Fragment, arg, navAnimationLeftToRight())
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}