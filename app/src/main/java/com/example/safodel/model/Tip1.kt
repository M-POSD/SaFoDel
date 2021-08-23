package com.example.safodel.model

import com.example.safodel.R

class Tip1 (var image: Int, var description: String) {
    companion object {
        fun initializeResultList(): MutableList<Tip1> {
            var tips: MutableList<Tip1> = ArrayList()
            tips.add(Tip1(R.drawable.ic_baseline_pedal_bike_24, "I am a bike button"))
            tips.add(Tip1(R.drawable.ic_baseline_arrow_back_24, "I am an arrow button"))
            tips.add(Tip1(R.drawable.ic_baseline_home_24, "I am a home button"))
            tips.add(Tip1(R.drawable.ic_baseline_cancel, "I am a cancel button"))
            tips.add(Tip1(R.drawable.ic_baseline_location_on_24, "I am a location button"))
            return tips
        }
    }
}