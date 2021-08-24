package com.example.safodel.model

import com.example.safodel.R

class Gear1 (var image: Int, var description: String) {
    companion object {
        fun initializeResultList(): MutableList<Gear1> {
            var gear1s: MutableList<Gear1> = ArrayList()
            gear1s.add(Gear1(R.drawable.ic_baseline_pedal_bike_24, "Helmet_v0"))
            gear1s.add(Gear1(R.drawable.ic_baseline_arrow_back_24, "Helmet_v1"))
            gear1s.add(Gear1(R.drawable.ic_baseline_home_24, "Helmet_v2"))
            gear1s.add(Gear1(R.drawable.ic_baseline_cancel, "Bicycle_v0"))
            gear1s.add(Gear1(R.drawable.ic_baseline_location_on_24, "Bicycle_v1"))
            gear1s.add(Gear1(R.drawable.back, "Bicycle_v2"))
            return gear1s
        }
    }
}