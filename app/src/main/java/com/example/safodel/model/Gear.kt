package com.example.safodel.model

import com.example.safodel.R

class Gear (var info_type: String, var image: Int, var title: String, var description: String, var statistics: String) {
    companion object {
        fun init(): MutableList<Gear> {
            var gears: MutableList<Gear> = ArrayList()
            gears.add(Gear("Gear",R.drawable.helmet1_v2, "Wear the right helmet",
            "Ensure it meets standards, there should be a certified sticker inside the helmet",
            ""))


            return gears
        }
    }
}



//gears.add(Gear("Recommendations",R.drawable.front_and_back_light, "Front & back lights",
//"A steady or flashing <font color='#EE0000'>white</font> light on the <font color='#EE0000'>front</font> of the bike that is visible for at least 200 metres <br><br>" +
//"A steady or flashing <font color='#EE0000'>red</font> light on the <font color='#EE0000'>rear</font> of the bike that is visible for at least 200 metres <br><br>" +
//"A <font color='#EE0000'>red</font> reflector on the rear of the bike that is visible for at least 50 metres when illuminated by a vehicle’s headlight on low beam",
//""))