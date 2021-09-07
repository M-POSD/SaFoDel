package com.example.safodel.model

import com.example.safodel.R

class   GroupCard2Data(
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

            // eBikeInfo3 data
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    "HIGH SPEED DANGER",
                    "Buying a high-speed e-bike may lead to faster deliveries but it is illegal",
                    "TIP: Be sure to check the government guidelines before buying an e-bike",
                    R.drawable.info1_2,
                    2)
            )
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    "HIGH THROTTLE",
                    "Too much initial throttle can cause injury",
                    "TIP: Get accustomed to riding an e-bike before actually going out for your first delivery",
                    R.drawable.info1_3,
                    1)
            )
            groupCard2Data.add(
                GroupCard2Data("eBikeInfo3",
                    "MORE THEFT",
                    "The higher price for e-bikes has seen more thefts of e-bikes over regular bikes",
                    "TIP: Make sure you lock your bike properly",
                    R.drawable.info1_1,
                    2)
            )

            // inAnAccident1 data
            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    "This is general advice only",
                    "",
                    "In the unfortunate event of a bike accident these are some steps to look after yourself",
                    0,
                    0)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    "Check on Yourself",
                    "",
                    "Make sure you’re okay, contact help if needed and try place yourself in a safe location " +
                            "if your accident was on the road",
                    R.drawable.info1_1,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    "Collect Details & Take Photos",
                    "",
                    "Collect details of those involved in accident and any witnesses " +
                            "as they may be necessary for insurance purposes later on",
                    R.drawable.info1_1,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    "See a Doctor",
                    "",
                    "Always give priority to your health and safety. " +
                            "Even if you feel it is only a small incident, it’s best to see a doctor",
                    R.drawable.info1_1,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident1",
                    "Report Incident to Police & Platform",
                    "",
                    "Report the incident to the police as well as let your platform know " +
                            "you’ve been in an accident as they may offer assistance",
                    R.drawable.info1_1,
                    1)
            )

            // inAnAccident2 data
            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    "",
                    "",
                    "Typically gig economy workers are treated as independent contractors and " +
                            "therefore lack some ordinary working protections and benefits",
                    0,
                    0)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    "Inconsistent Rules",
                    "",
                    "It is important to know that  food delivery platforms have different working contracts and " +
                            "manage the working relationship differently",
                    0,
                    2)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    "Employee or Not?",
                    "",
                    "Uber Eats treats it’s riders as independent contractors while " +
                            "a recent ruling Deliveroo drivers as  employees",
                    0,
                    1)
            )

            groupCard2Data.add(
                GroupCard2Data("inAnAccident2",
                    "Growing Area",
                    "",
                    "How gig workers are treated is a growing area, " +
                            "groups like the transport workers union (TWU) are fighting for gig workers to have more rights",
                    0,
                    2)
            )



            return groupCard2Data
        }
    }
}