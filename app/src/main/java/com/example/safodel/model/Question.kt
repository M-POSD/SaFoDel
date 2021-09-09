package com.example.safodel.model

import android.util.Log
import com.example.safodel.R
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class Question(
    var id: Int,
    var question: String,
    var image: Int,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var answer: Int,
    var information: String

) {
    companion object {
        fun init(): MutableList<Question> {
            var questions: MutableList<Question> = ArrayList()

            questions.add(Question(
                    1, "What is the maximum speed you can ride your e-bike?",
                0,
                "25 KM/H",
                "30 KM/H",
                "35 KM/H",
                "50 KM/H",
                1,
                "The Australian government has set a strict speed limit of 25 Km/h on e-bikes."
            ))
            questions.add(Question(
                2, "Even if a road has a bike lane, can you ignore riding on it?",
                0,
                "Yes",
                "No",
                "",
                "",
                2,
                "Ride in bike lanes where they are provided, unless impractical to do so."
            ))
            questions.add(Question(
                3, "As a delivery rider, should you stop when you see tram passengers " +
                        "getting off? ",
                0,
                "Yes",
                "No",
                "",
                "",
                1,
                "Bike riders must stop behind a tram that has stopped at a tram stop, " +
                        "to allow passengers to get on and off safely."
            ))
            questions.add(Question(
                4, "Should you signal when turning right?",
                0,
                "Yes",
                "No",
                "",
                "",
                1,
                "Bike riders must signal when turning right, " +
                        "and can help share the road more safely by riding predictably, " +
                        "using eye contact and indicating all changes of direction."
            ))
            questions.add(Question(
                5, "Are you allowed to ride on a footpath while delivering food?",
                0,
                "Yes",
                "No",
                "",
                "",
                2,
                "Any bike rider must use a front light, rear light (flashing or steady), " +
                        "and a rear red reflector when riding at night or " +
                        "in conditions where visibility is poor."
            ))
            questions.add(Question(
                6, "Whenever you are changing lanes, " +
                        "is it not your responsibility to give way and look for vehicles already " +
                        "in that lane?",
                0,
                "Yes",
                "No",
                "",
                "",
                2,
                "Whenever you change lanes from one marked lane or line of traffic to another, " +
                        "you must give way to vehicles already in that lane or line of traffic.."
            ))
            questions.add(Question(
                7, "You can wear any helmet while delivering, regardless of its ratings.",
                0,
                "True",
                "False",
                "",
                "",
                2,
                "Cyclists need to wear a securely fitted and " +
                        "fastened helmet with a mark of compliance with the Australian Standard"
            ))

            var questionList: MutableList<Question> = ArrayList()
            for (i in 1..5) {
                var random = nextInt(0, questions.size - 1)
                questionList.add(questions.removeAt(random))
            }

            return questionList
        }

    }
}