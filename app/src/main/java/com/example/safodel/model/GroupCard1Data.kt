package com.example.safodel.model

import com.example.safodel.R

class GroupCard1Data(
    var dataType: String,
    var description_id: Int,
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
                    R.string.tip2_0_description,
                    0,
                    0
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    R.string.tip2_1_description,
                    R.drawable.tip2_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    R.string.tip2_2_description,
                    R.drawable.tip2_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    R.string.tip2_3_description,
                    R.drawable.tip2_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "tip2",
                    R.string.tip2_4_description,
                    R.drawable.tip2_4,
                    1
                )
            )

            // eBikeInfo1 data
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    R.string.info1_1_description,
                    R.drawable.info2_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    R.string.info1_2_description,
                    R.drawable.info2_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    R.string.info1_3_description,
                    R.drawable.info2_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo1",
                    R.string.info1_4_description,
                    R.drawable.info2_4,
                    1
                )
            )

            // eBikeInfo2 data
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    R.string.info2_1_description,
                    R.drawable.info3_1,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    R.string.info2_2_description,
                    R.drawable.info3_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "eBikeInfo2",
                    R.string.info2_3_description,
                    R.drawable.info3_3,
                    2
                )
            )

            // gear data
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    R.string.gear1_1_description,
                    R.drawable.gear1_1,
                    3
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    R.string.gear1_2_description,
                    R.drawable.gear1_2,
                    1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    R.string.gear1_3_description,
                    R.drawable.gear1_3,
                    2
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "gear",
                    R.string.gear1_4_description,
                    R.drawable.gear1_4,
                    1
                )
            )

            // inAnAccident3 data
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.in_an_accident3_0_description,
                    0,
                    0
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.in_an_accident3_1_description,
                    R.drawable.inanaccident3_1,
                    2
                )
            )
//            groupCard1Data.add(
//                GroupCard1Data(
//                    "inAnAccident3",
//                    R.string.uber_link,
//                    R.drawable.food_delivery_1,
//                    2
//                )
//            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.uber_link,
                    R.drawable.food_delivery_2,
                    -1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.deliveroo_link,
                    R.drawable.food_delivery_3,
                    -1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.doordash_link,
                    R.drawable.food_delivery_4,
                    -1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.easi_link,
                    R.drawable.food_delivery_5,
                    -1
                )
            )
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    R.string.in_an_accident3_2_description,
                    R.drawable.inanaccident3_2,
                    2
                )
            )

            return groupCard1Data
        }
    }
}