package com.example.safodel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic3Binding
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment

class Epic3Fragment : BasicFragment<FragmentEpic3Binding>(FragmentEpic3Binding::inflate) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic3Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        setToolbar2(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}