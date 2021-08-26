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



//gears.add(Gear("Recommendations",R.drawable.front_and_back_light, "Front & back lights",
//"A steady or flashing <font color='#EE0000'>white</font> light on the <font color='#EE0000'>front</font> of the bike that is visible for at least 200 metres <br><br>" +
//"A steady or flashing <font color='#EE0000'>red</font> light on the <font color='#EE0000'>rear</font> of the bike that is visible for at least 200 metres <br><br>" +
//"A <font color='#EE0000'>red</font> reflector on the rear of the bike that is visible for at least 50 metres when illuminated by a vehicle’s headlight on low beam",
//""))