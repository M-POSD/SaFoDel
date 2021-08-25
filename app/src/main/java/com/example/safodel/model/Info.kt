package com.example.safodel.model

import com.example.safodel.R

class Info (var info_name: String, var title: String, var description: String, var statistics: String) {
    companion object {
        fun initializeResultList(): MutableList<Info> {
            var infos: MutableList<Info> = ArrayList()
            infos.add(Info("Risk", "Watch your speed",
                "E-bikes travel much faster than regular bicycles thus be prepared to travel faster, and be mindful other road users may not expect you to be travelling so fast",
                ""))
            infos.add(Info("Risk", "Mind the weight",
                "Some e-bike models are much heavier than regular bicycles, and injuries have occurred as people mount or dismount their e-bikes",
                ""))
            infos.add(Info("Advantage", "Less effort",
                "Using an e-bike can offer a more comfortable ride and added assistance, especially going up hills, or travelling long distances",
                ""))
            infos.add(Info("Advantage", "Low rental cost",
                "Renting an ebike can be relatively inexpensive, yet offer you the speed and versatility to make many deliveries \n" +
                    "\nThey also don’t have any additional costs like petrol or registration fees",
                ""))
            infos.add(Info("Rule", "Max speed limited to 25km/h ",
                "Highest speed should be at 25km/h",
                ""))
            infos.add(Info("Rule", "Max motor power should be 250 watts (or 200 depending on e-bike)",
                "A bicycle with an attached auxiliary motor should not produce power over 200 watts\n" +
                        "\nAn electrically power assisted cycle should not have a motor exceeding 250 watts\n",
                ""))
            return infos
        }
    }
}