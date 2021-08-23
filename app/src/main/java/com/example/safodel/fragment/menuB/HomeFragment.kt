package com.example.safodel.fragment.menuB

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentHomeBinding
import com.example.safodel.fragment.BasicFragment
import android.content.SharedPreferences





class HomeFragment : BasicFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val toolbar = binding.toolbar.root
        setToolbarBasic(toolbar)

        binding.epicCard12.editTextLeft.text = "Ride Safer"
        binding.epicCard12.editTextRight.text = "Delivery on e-bike"
        binding.epicCard34.editTextLeft.text = "Safety Gear"
        binding.epicCard34.editTextRight.text = "Placeholder"

        binding.epicCard12.cardLeft.setOnClickListener() {
            recordPosition(0)
            findNavController().navigate(R.id.epicsFragment, null, navAnimation())
        }

        binding.epicCard12.cardRight.setOnClickListener() {
            recordPosition(1)
            findNavController().navigate(R.id.epicsFragment, null, navAnimation())
        }

        binding.epicCard34.cardLeft.setOnClickListener() {
            recordPosition(2)
            findNavController().navigate(R.id.epicsFragment, null, navAnimation())
        }
        return binding.root
    }

    private fun navAnimation() : NavOptions {
        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right).build()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun recordPosition(position: Int) {
        val sharedPref = requireActivity().applicationContext.getSharedPreferences(
            "epicPosition", Context.MODE_PRIVATE
        )

        val spEditor = sharedPref.edit()
        spEditor.putString("epicPosition", "" + position)
        spEditor.apply()
    }
}