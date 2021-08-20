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
    lateinit var flipFront : AnimatorSet
    lateinit var flipBack : AnimatorSet
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

//        flipCard() => able card to flip

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

    private fun flipCard() {
        flipFront = AnimatorInflater.loadAnimator(
            activity?.applicationContext,
            R.animator.flip_front_animator) as AnimatorSet

        flipBack = AnimatorInflater.loadAnimator(
            activity?.applicationContext,
            R.animator.flip_back_animator) as AnimatorSet

        val scale : Float = requireActivity().applicationContext.resources.displayMetrics.density
        Log.d("Scale", "" + scale)

        binding.card1Front.card.cameraDistance = 8000 * scale
        binding.card1Back.card.cameraDistance = 8000 * scale
        binding.card2Front.card.cameraDistance = 8000 * scale
        binding.card2Back.card.cameraDistance = 8000 * scale


        binding.card1Front.card.setOnClickListener() {
            if (isFrontArray[0]) {
                flipFront.setTarget(binding.card1Front.card)
                flipBack.setTarget(binding.card1Back.card)
                flipFront.start()
                flipBack.start()
                isFrontArray[0] = false
            } else {
                flipFront.setTarget(binding.card1Back.card)
                flipBack.setTarget(binding.card1Front.card)
                flipFront.start()
                flipBack.start()
                isFrontArray[0] = true
            }

        }

        binding.card2Front.card.setOnClickListener() {
            if (isFrontArray[1]) {
                flipFront.setTarget(binding.card2Front.card)
                flipBack.setTarget(binding.card2Back.card)
                flipFront.start()
                flipBack.start()
                isFrontArray[1] = false
            } else {
                flipFront.setTarget(binding.card2Back.card)
                flipBack.setTarget(binding.card2Front.card)
                flipFront.start()
                flipBack.start()
                isFrontArray[1] = true
            }

        }
    }

}

//        val mainActivity = activity as MainActivity
//        mainActivity.isBottomNavigationVisible(false)

//        toolbar.setBackgroundResource(R.color.skin) => set toolbar background color not null


// animator refers from https://www.youtube.com/watch?v=DnXWcGmLHHs&ab_channel=doctorcode