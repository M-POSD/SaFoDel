package com.example.safodel.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.safodel.databinding.FragmentEpic1Binding
import android.widget.Toast
import android.widget.Toolbar

class Epic1Fragment : Fragment() {
    private var _binding: FragmentEpic1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}