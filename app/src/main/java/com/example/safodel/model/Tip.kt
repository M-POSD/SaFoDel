package com.example.safodel.model

class Tip (var tip_id: Int, var title: String, var description: String, var statistics: String) {
    companion object {
        fun initializeResultList(): MutableList<Tip> {
            var tips: MutableList<Tip> = ArrayList()
            tips.add(
                Tip(1,"Be predictable ","-  Obeying stop and give way signs\n" +
                    "-  Don’t ride in vehicle blind spots\n" +
                    "-  Signal your turns\n", "")
            )
            tips.add(
                Tip(1,"Stay alert", "-  Be alert to traffic\n" +
                    "-  Watch out for people getting out of parked vehicles\n" +
                    "-  Don’t be distracted by phone (use hands-free for GPS)\n", "")
            )
            tips.add(
                Tip(1,"Hook turn rather than turn right at intersections", "-  Be alert to traffic\n" +
                    "-  Watch out for people getting out of parked vehicles\n" +
                    "-  Don’t be distracted by phone\n", "")
            )
            tips.add(
                Tip(2,"Be visible","-  Should ride with front light visible from 200m\n" +
                        "-  Back light (red) visible from 200m\n" +
                        "-  Reflector at back visible from 50m\n" +
                        "-  Wear bright clothing \n", "")
            )
            tips.add(
                Tip(2,"Take well lit routes", "-  Try to ride along routes or paths with sufficient lighting",
                    "A higher proportion of serious injury bicycle accidents in Victoria occur under dark settings")
            )
            return tips
        }
    }
}