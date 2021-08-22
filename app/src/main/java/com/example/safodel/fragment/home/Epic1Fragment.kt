package com.example.safodel.fragment.home

import android.os.Bundle
import android.view.*
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentEpic1Binding
import com.example.safodel.fragment.BasicFragment


class Epic1Fragment : BasicFragment<FragmentEpic1Binding>(FragmentEpic1Binding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpic1Binding.inflate(inflater,container,false)

        setDefaultView()

        binding.tip1Card.card.setOnClickListener() {
            findNavController().navigate(R.id.tip1Fragment, null, navAnimation())
        }

        return binding.root
    }

    private fun setDefaultView() {
        binding.tip1Card.editText.text = "Tip 1"
        binding.tip2Card.editText.text = "Tip 2"
        binding.tip3Card.editText.text = "Tip 3"
        binding.tip4Card.editText.text = "Tip 4"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navAnimation() : NavOptions{
        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right).build()
    }

}