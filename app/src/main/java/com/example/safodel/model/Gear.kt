package com.example.safodel.model

import com.example.safodel.R

class Gear (var info_type: String, var image: Int, var title: String, var description: String, var statistics: String) {
    companion object {
        fun initializeResultList(): MutableList<Gear> {
            var gears: MutableList<Gear> = ArrayList()
            gears.add(Gear("Gear",R.drawable.helmet1_v2, "Wear the right helmet",
            "Ensure it meets standards, there should be a certified sticker inside the helmet",
            ""))
            gears.add(Gear("Standard",R.drawable.helmet_standard, "Helmets must be certified",
                "Labelled with a sticker inside the helmet, there is an Australian and NZ standard for helmets",
                "Reduce chance of serious head injury by 70% (2016 Study)"))
            gears.add(Gear("Recommendations",R.drawable.book, "Front & back lights",
                "Labelled with a sticker inside the helmet, there is an Australian and NZ standard for helmets",
                "Reduce chance of serious head injury by 70% (2016 Study)"))

            return gears
        }
    }
}