package com.example.safodel.model

class Tip (var tip_id: Int, var title: String, var description: String, var statistics: String) {
    companion object {
        fun initializeResultList(): MutableList<Tip> {
            var tips: MutableList<Tip> = ArrayList()
            tips.add(
                Tip(1,"Be predictable ","Obeying stop and give way signs\n" +
                    "\nDon’t ride in vehicle blind spots\n" +
                    "\nSignal your turns", "")
            )
            tips.add(
                Tip(1,"Stay alert", "Be alert to traffic\n" +
                    "\nWatch out for people getting out of parked vehicles\n" +
                    "\nDon’t be distracted by phone (use hands-free for GPS)", "")
            )
            tips.add(
                Tip(1,"Hook turn rather than turn right at intersections", "Be alert to traffic\n" +
                    "\nWatch out for people getting out of parked vehicles\n" +
                    "\nDon’t be distracted by phone", "")
            )
            tips.add(
                Tip(2,"Be visible","Should ride with front light visible from 200m\n" +
                        "\nBack light (red) visible from 200m\n" +
                        "\nReflector at back visible from 50m\n" +
                        "\nWear bright clothing", "")
            )
            tips.add(
                Tip(2,"Take well lit routes", "\nTry to ride along routes or paths with sufficient lighting",
                    "A higher proportion of serious injury bicycle accidents in Victoria occur under dark settings")
            )
            return tips
        }
    }
}