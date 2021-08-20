package com.example.safodel.fragment.home.epic1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safodel.R
import com.example.safodel.databinding.FragmentTip1Binding
import com.example.safodel.fragment.BasicFragment
import com.example.safodel.ui.main.MainActivity

class Tip1Fragment : BasicFragment<FragmentTip1Binding>(FragmentTip1Binding::inflate){
    lateinit var flip_front : AnimatorSet
    lateinit var flip_back : AnimatorSet
    val isFrontArray: BooleanArray = booleanArrayOf(true, true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTip1Binding.inflate(inflater,container,false)
        val toolbar = binding.toolbar.root

        var title1 = "title1"
        var title2 = "title2"
        binding.card1Front.editText.text = title1
        binding.card2Front.editText.text = title2
        binding.card1Back.editText.text = tempTipMap(title1)
        binding.card2Back.editText.text = tempTipMap(title2)

        flip_front = AnimatorInflater.loadAnimator(
            activity?.applicationContext,
            R.animator.flip_front_animator) as AnimatorSet

        flip_back = AnimatorInflater.loadAnimator(
            activity?.applicationContext,
            R.animator.flip_back_animator) as AnimatorSet

        val scale : Float = requireActivity().applicationContext.resources.displayMetrics.density
        Log.d("Scale", "" + scale)
        binding.card1Front.cardLayout.cameraDistance = 8000 * scale
        binding.card1Back.cardLayout.cameraDistance = 8000 * scale
        binding.card2Front.cardLayout.cameraDistance = 8000 * scale
        binding.card2Back.cardLayout.cameraDistance = 8000 * scale


        binding.card1Front.card.setOnClickListener() {
            if (isFrontArray[0]) {
                flip_front.setTarget(binding.card1Front.card)
                flip_back.setTarget(binding.card1Back.card)
                flip_front.start()
                flip_back.start()
                isFrontArray[0] = false
            } else {
                flip_front.setTarget(binding.card1Back.card)
                flip_back.setTarget(binding.card1Front.card)
                flip_front.start()
                flip_back.start()
                isFrontArray[0] = true
            }

        }

        binding.card2Front.card.setOnClickListener() {
            if (isFrontArray[1]) {
                flip_front.setTarget(binding.card2Front.card)
                flip_back.setTarget(binding.card2Back.card)
                flip_front.start()
                flip_back.start()
                isFrontArray[1] = false
            } else {
                flip_front.setTarget(binding.card2Back.card)
                flip_back.setTarget(binding.card2Front.card)
                flip_front.start()
                flip_back.start()
                isFrontArray[1] = true
            }

        }

        setToolbarCancel(toolbar)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun tempTipMap(key: String): String? {
        var tipMap : HashMap<String, String> = HashMap<String, String> ()
        tipMap["title1"] = "Hi My name is Hsuan! How are u today!"
        tipMap["title2"] = "Hi My name is CJ! Please call CJ not DJ!"
        return tipMap[key]
    }

}

//        val mainActivity = activity as MainActivity
//        mainActivity.isBottomNavigationVisible(false)

//        toolbar.setBackgroundResource(R.color.skin) => set toolbar background color not null


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode