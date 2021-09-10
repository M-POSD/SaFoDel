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
                    "Take extra care when cycling at night. It is harder for drivers to see you and for you to see hazards",
                    0,
                    0
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Try to ride on well-lit roads and wear bright or light coloured clothing or a reflective vest",
                    R.drawable.tip2_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Always use front and rear lights",
                    R.drawable.tip2_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Keep backup lights or a portable charger",
                    R.drawable.tip2_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    "Avoid delivering in areas with poor lighting",
                    R.drawable.tip2_4,
                    1
                )
            )

            // eBikeInfo1 data
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    "Make riding twice as easy as push bikes",
                    R.drawable.info2_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    "Comes in handy when riding up hills or over long distances",
                    R.drawable.info2_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    "Promote sustainability",
                    R.drawable.info2_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    "No need to break the bank as they’re generally cheaper to rent",
                    R.drawable.info2_4,
                    1
                )
            )

            // eBikeInfo2 data
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    "The Victorian Government allows a maximum power of 250 watts",
                    R.drawable.info3_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    "A maximum speed of 25 km/h is allowed if the rider is pedalling",
                    R.drawable.info3_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    "All other rules are the same as pedal bicycles",
                    R.drawable.info3_3,
                    2
                )
            )

            // gear data
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Bicycle helmets reduce the chances of a serious head injury by almost 70%",
                    R.drawable.gear1_1,
                    3
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Warm gloves not only protect you from cold but also help you maintain safe grip at all times",
                    R.drawable.gear1_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "With the lockdown restrictions on, food delivery companies require masks or face covers for all drivers",
                    R.drawable.gear1_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    "Always be prepared for bad weather and carry a rain jacket in your delivery bag",
                    R.drawable.gear1_4,
                    1
                )
            )

            // inAnAccident3 data
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Most delivery companies offer a support package for driver and delivery partners, " +
                            "helping to cover partners should something go wrong while using their platform ",
                    0,
                    0
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "To find out the insurance details provided by your food delivery platform, " +
                            "select your partner below",
                    R.drawable.inanaccident3_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Click to Know More",
                    R.drawable.food_delivery_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Click to Know More",
                    R.drawable.food_delivery_2,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Click to Know More",
                    R.drawable.food_delivery_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Click to Know More",
                    R.drawable.food_delivery_4,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "Click to Know More",
                    R.drawable.food_delivery_5,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    "The TAC (Transport Accident Commission) provides No Fault Benefits under certain terms and conditions",
                    R.drawable.inanaccident3_2,
                    2
                )
            )

            return groupCard1Data
        }
    }
}