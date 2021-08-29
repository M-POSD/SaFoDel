package com.example.safodel.model

import com.example.safodel.R

class GroupCard2Data(
    var dataType: String,
    var title: String,
    var statistics: String,
    var description: String,
    var image: Int,
    var cardType: Int
) {
    companion object {
        fun init(): MutableList<GroupCard2Data> {
            var groupCard2Data: MutableList<GroupCard2Data> = ArrayList()

            // tip1 data
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    "RIDE CAREFULLY AT INTERSECTIONS",
                    "Bicycle crashes are marginally more common (55%) at intersections",
                    "TIP: If you want to turn right at any intersection, " +
                            "doing a hook turn is often a safer option (unless a sign prohibits it).",
                    R.drawable.tip1_1,2)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    "STOP AND GIVE WAY",
                    "15% of bike rider casualties occur when a driver or rider who is turning right fails to give way to an oncoming vehicle travelling straight through.",
                    "TIP: At intersections without traffic lights, signs or road lines, give way to any vehicle entering or approaching the intersection from your right.",
                    R.drawable.tip1_2,1)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    "AVOID DOORING",
                    "8% of bike rider casualties occur when a driver opens their door into the path of a bike rider.",
                    "TIP: While it is never the rider’s fault when hit by an opening door, you must be aware of the risk and position themselves so that any chance of a collision is reduced.",
                    R.drawable.tip1_3,2)
            )
            groupCard2Data.add(
                GroupCard2Data("tip1",
                    "STAY AWARE",
                    "Delivery Partners recommend you not to listen to music while delivering",
                    "Don’t be distracted by the use of phone or listening to music while delivering. Instead use hands-free GPS",
                    R.drawable.tip1_4,1)
            )

            // ebikeinfo1 data
            groupCard2Data.add(
                GroupCard2Data("ebikeinfo1",
                    "MORE THEFT",
                    "The higher price tags have also led to more e-bikes being stolen than a standard bicycle",
                    "TIP: Make sure you lock your bike properly",
                    R.drawable.info1_1,
                    2)
            )
            groupCard2Data.add(
                GroupCard2Data("ebikeinfo1",
                    "HIGH SPEED DANGER",
                    "Buying a high-speed e-bike may lead to faster deliveries but it is illegal",
                    "TIP: Be sure to check the government guidelines before buying an e-bike",
                    R.drawable.info1_2,
                    1)
            )
            groupCard2Data.add(
                GroupCard2Data("ebikeinfo1",
                    "HIGH THROTTLE",
                    "Too Much Initial Throttle Can Cause Minor Injuries",
                    "TIP: Get accustomed to riding an e-bike before actually going out for your first deliveryEBikeInfo1",
                    R.drawable.info1_3,
                    2)
            )
            return groupCard2Data
        }
    }
}