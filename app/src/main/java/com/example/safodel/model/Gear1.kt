package com.example.safodel.model

import com.example.safodel.R

class Gear1 (var image: Int,var title: String, var description: String, var statistics: String) {
    companion object {
        fun initializeResultList(): MutableList<Gear1> {
            var gear1s: MutableList<Gear1> = ArrayList()
            gear1s.add(Gear1(R.drawable.helmet1_v2, "Wear the right helmet",
            "Ensure it meets standards, there should be a certified sticker inside the helmet\n",
            ""))
            return gear1s
        }
    }
}