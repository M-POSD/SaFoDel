package com.example.safodel.model

import com.example.safodel.R

class GearStandard (var image: Int, var title: String) {
    companion object {
        fun init(): MutableList<GearStandard> {
            var standards: MutableList<GearStandard> = ArrayList()

            standards.add(GearStandard(R.drawable.bsi, "BSI"))
            standards.add(GearStandard(R.drawable.global_mark, "Global-Mark"))
            standards.add(GearStandard(R.drawable.sai_global, "SAI Global"))
            standards.add(GearStandard(R.drawable.aus, "AUS"))

            return standards
        }
    }
}