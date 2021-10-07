package com.example.safodel.model

import com.example.safodel.R

/**
 * for the static info only contains image and description
 */
class GroupCard1Data(
    var dataType: String,
    var description_id: Int,
    var image: Int,
    var cardType: Int
) {
    companion object {
        fun init(): MutableList<GroupCard1Data> {
            val groupCard1Data: MutableList<GroupCard1Data> = ArrayList()

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

            // eBikeInfo3 data
            groupCard1Data.add(
                GroupCard1Data("eBikeInfo3",
                    R.string.info3_1_description,
                    R.drawable.info1_2,
                    2)
            )
            groupCard1Data.add(
                GroupCard1Data("eBikeInfo3",
                    R.string.info3_2_description,
                    R.drawable.info1_3,
                    1)
            )
            groupCard1Data.add(
                GroupCard1Data("eBikeInfo3",
                    R.string.info3_3_description,
                    R.drawable.info1_4,
                    2)
            )
            groupCard1Data.add(
                GroupCard1Data("eBikeInfo3",
                    R.string.info3_4_description,
                    R.drawable.info1_1,
                    2)
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

            /* For the connection between icons and websites  */
            groupCard1Data.add(
                GroupCard1Data(
                    "inAnAccident3",
                    0,
                    0,
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