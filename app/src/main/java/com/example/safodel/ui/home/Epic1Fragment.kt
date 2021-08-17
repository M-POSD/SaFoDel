package com.example.safodel.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.safodel.databinding.FragmentEpic1Binding
import android.widget.Toast
import androidx.appcompat.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment


class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root
        setToolbar2(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}