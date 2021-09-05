package com.example.safodel.fragment.menuL.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.safodel.R
import com.example.safodel.databinding.FragmentExamFinishBinding
import com.example.safodel.fragment.BasicFragment


class ExamFinishFragment : BasicFragment<FragmentExamFinishBinding>(FragmentExamFinishBinding::inflate) {
    private lateinit var toast: Toast
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamFinishBinding.inflate(inflater, container, false)

        toast = Toast.makeText(requireActivity(),null,Toast.LENGTH_SHORT)
        configDefaultView()
        val toolbar = binding.toolbar.root
        setToolbarWhite(toolbar)
        configBtnOnClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configDefaultView() {
        val args = getArgument()
        val accuracy = args[0].toFloat() / args[1].toFloat()
        binding.level.text = "${args[0]} / ${args[1]}"
        when {
            accuracy < 0.6 -> {
                binding.image.setImageResource(R.drawable.failure)
                binding.title.text = "Learning rules again"
            }
            accuracy < 1f -> {
                binding.image.setImageResource(R.drawable.medal)
                binding.title.text = "Review rules sometimes"
            }
            accuracy == 1f -> {
                binding.image.setImageResource(R.drawable.campion)
                binding.title.text = "Perfect !!!"
            }
        }
    }

    private fun configBtnOnClickListener() {
        binding.returnButton.setOnClickListener {
            findNavController().navigate(R.id.examFragment, null, navAnimationLeftToRight())
        }
        binding.ruleInfoButton.setOnClickListener {
            toast.setText("PlaceHolder Right Now!")
            toast.show()
        }
    }

    private fun getArgument(): IntArray {
        val args = IntArray(2)
        args[0] = (arguments?.get("score") ?: 0) as Int
        args[1] = (arguments?.get("numOfQuestions") ?: 5) as Int

        return args
    }
}