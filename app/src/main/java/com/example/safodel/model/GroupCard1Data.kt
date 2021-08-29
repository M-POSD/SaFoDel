package com.example.safodel.model

import com.example.safodel.R

class GroupCard1Data(
    var dataType: String,
    var description: String,
    var image: Int,
    var cardType: Int
) {
    companion object {
        fun init(): MutableList<GroupCard1Data> {
            var groupCard1Data: MutableList<GroupCard1Data> = ArrayList()

            // tip2 data
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Take extra care when cycling at night. It is harder for drivers to see you and for you to see hazards.",
                    0,
                    0
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Try to ride on well-lit roads and wear bright or light coloured clothing or a reflective vest.",
                    R.drawable.book,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Always use front and rear lights.",
                    R.drawable.book,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Keep backup lights or a portable charger.",
                    R.drawable.book,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Avoid delivering in areas with poor lighting.",
                    R.drawable.book,
                    1
                )
            )

            // ebikeinfo2 data
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo2",
                    "Make riding twice as easy as push bikes",
                    R.drawable.book,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo2",
                    "Particularly come in handy when riding up hills or over long distances",
                    R.drawable.book,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo2",
                    "Promote the idea of sustainability",
                    R.drawable.book,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo2",
                    "No need to break your bank because of the low rental cost",
                    R.drawable.book,
                    1
                )
            )

            // ebikeinfo3 data
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo3",
                    "Victorian government allows a maximum power of 250 watts",
                    R.drawable.book,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo3",
                    "A maximum speed of 25 Km/h is allowed if the rider is pedalling",
                    R.drawable.book,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "ebikeinfo3",
                    "All other rules are the same as pedal bicycles",
                    R.drawable.book,
                    2
                )
            )

            // gear data
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Bicycle helmets reduce the chances of a serious head injury by almost 70%",
                    R.drawable.helmet1_v2,
                    3
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Warm Gloves not only protect you from cold but also help you maintain safe grip at all times",
                    R.drawable.helmet1_v2,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "With the lockdown restrictions on, food delivery companies require masks or face covers for every driver",
                    R.drawable.helmet1_v2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Always be prepared for bad weather and carry a rain jacket in your delivery bag.",
                    R.drawable.helmet1_v2,
                    2
                )
            )

            return groupCard1Data
        }
    }
}