package com.example.safodel.model

import com.example.safodel.R

class   GroupCard2Data(
    var dataType: String,
    var title_id: Int,
    var statistics_id: Int,
    var description_id: Int,
    var image: Int,
    var cardType: Int
) {
    companion object {
        fun init(): MutableList<GroupCard2Data> {
            var groupCard2Data: MutableList<GroupCard2Data> = ArrayList()

            // tip1 data
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    R.string.tip1_1_title,
                    R.string.tip1_1_statistics,
                    R.string.tip1_1_description,
                    R.drawable.tip1_1,2)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    R.string.tip1_2_title,
                    R.string.tip1_2_statistics,
                    R.string.tip1_2_description,
                    R.drawable.tip1_2,1)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    R.string.tip1_3_title,
                    R.string.tip1_3_statistics,
                    R.string.tip1_3_description,
                    R.drawable.tip1_3,2)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    R.string.tip1_4_title,
                    R.string.tip1_4_statistics,
                    R.string.tip1_4_description,
                    R.drawable.tip1_4,1)
            )

            // eBikeInfo3 data
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    R.string.info3_1_title,
                    R.string.info3_1_statistics,
                    R.string.info3_1_description,
                    R.drawable.info1_2,
                    2)
            )
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    R.string.info3_2_title,
                    R.string.info3_2_statistics,
                    R.string.info3_2_description,
                    R.drawable.info1_3,
                    1)
            )
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    R.string.info3_3_title,
                    R.string.info3_3_statistics,
                    R.string.info3_3_description,
                    R.drawable.info1_1,
                    2)
            )

            // inAnAccident1 data
            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    R.string.in_an_accident1_0_title,
                    0,
                    R.string.in_an_accident1_0_description,
                    0,
                    4)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    R.string.in_an_accident1_1_title,
                    0,
                    R.string.in_an_accident1_1_description,
                    R.drawable.inanaccident1_1,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    R.string.in_an_accident1_2_title,
                    0,
                    R.string.in_an_accident1_2_description,
                    R.drawable.inanaccident1_2,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    R.string.in_an_accident1_3_title,
                    0,
                    R.string.in_an_accident1_3_description,
                    R.drawable.inanaccident1_3,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    R.string.in_an_accident1_4_title,
                    0,
                    R.string.in_an_accident1_4_description,
                    R.drawable.inanaccident1_4,
                    1)
            )

            // inAnAccident2 data
            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    0,
                    0,
                    R.string.in_an_accident2_0_description,
                    0,
                    4)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    R.string.in_an_accident2_1_title,
                    0,
                    R.string.in_an_accident2_2_description,
                    R.drawable.inanaccident2_1,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    R.string.in_an_accident2_2_title,
                    0,
                    R.string.in_an_accident2_2_description,
                    R.drawable.inanaccident2_2,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    R.string.in_an_accident2_3_title,
                    0,
                    R.string.in_an_accident2_3_description,
                    R.drawable.inanaccident2_3,
                    2)
            )

            // roadSign data -> for tip3 currently
            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign1_title,
                    0,
                    R.string.road_sign1_description,
                    R.drawable.road_sign_1,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign2_title,
                    0,
                    R.string.road_sign2_description,
                    R.drawable.road_sign_2,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign3_title,
                    0,
                    R.string.road_sign3_description,
                    R.drawable.road_sign_3,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign4_title,
                    0,
                    R.string.road_sign4_description,
                    R.drawable.road_sign_4,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign5_title,
                    0,
                    R.string.road_sign5_description,
                    R.drawable.road_sign_5,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("roadSign",
                    R.string.road_sign6_title,
                    0,
                    R.string.road_sign6_description,
                    R.drawable.road_sign_6,
                    1)
            )


            return groupCard2Data
        }
    }
}