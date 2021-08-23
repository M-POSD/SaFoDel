package com.example.safodel.model

import com.example.safodel.R

class Info1 (var image: Int, var description: String) {
    companion object {
        fun initializeResultList(): MutableList<Info1> {
            var infos: MutableList<Info1> = ArrayList()
            infos.add(Info1(R.drawable.ic_baseline_pedal_bike_24, "I am CJ"))
            infos.add(Info1(R.drawable.ic_baseline_arrow_back_24, "I am Hsuan"))
            infos.add(Info1(R.drawable.ic_baseline_home_24, "I am Francis"))
            infos.add(Info1(R.drawable.ic_baseline_cancel, "I am James"))
            infos.add(Info1(R.drawable.ic_baseline_location_on_24, "I am Suvansh"))
            return infos
        }
    }
}