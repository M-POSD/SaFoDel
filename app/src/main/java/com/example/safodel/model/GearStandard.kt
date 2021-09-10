package com.example.safodel.model

import com.example.safodel.R

class GearStandard (var image: Int, var title_id: Int) {
    companion object {
        fun init(): MutableList<GearStandard> {
            var standards: MutableList<GearStandard> = ArrayList()

            standards.add(GearStandard(R.drawable.bsi, R.string.gear_standard_bsi))
            standards.add(GearStandard(R.drawable.global_mark, R.string.gear_standard_global_mark))
            standards.add(GearStandard(R.drawable.sai_global, R.string.gear_standard_sai_global))
            standards.add(GearStandard(R.drawable.aus, R.string.gear_standard_aus))

            return standards
        }
    }
}