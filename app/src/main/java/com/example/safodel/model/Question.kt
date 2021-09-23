package com.example.safodel.model

import android.util.Log
import com.example.safodel.R
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class Question(
    var id: Int,
    var question: Int,
    var image: Int,
    var option1: Int,
    var option2: Int,
    var option3: Int,
    var option4: Int,
    var answer: Int,
    var information: Int

) {
    companion object {
        fun init(): MutableList<Question> {
            var questions: MutableList<Question> = ArrayList()

            questions.add(Question(
                    1,
                R.string.question1_heading,
                0,
                R.string.question1_option1,
                R.string.question1_option2,
                R.string.question1_option3,
                R.string.question1_option4,
                1,
                R.string.question1_information
            ))
            questions.add(Question(
                2, R.string.question2_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                2,
                R.string.question2_information
            ))
            questions.add(Question(
                3, R.string.question3_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                1,
                R.string.question3_information
            ))
            questions.add(Question(
                4, R.string.question4_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                1,
                R.string.question4_information
            ))
            questions.add(Question(
                5, R.string.question5_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                2,
                R.string.question5_information
            ))
            questions.add(Question(
                6, R.string.question6_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                1,
                R.string.question6_information
            ))
            questions.add(Question(
                7, R.string.question7_heading,
                0,
                R.string.true_text,
                R.string.false_text,
                0,
                0,
                2,
                R.string.question7_information
            ))
            questions.add(Question(
                8, R.string.question8_heading,
                0,
                R.string.yes,
                R.string.no,
                0,
                0,
                2,
                R.string.question8_information
            ))
            questions.add(Question(
                9, R.string.question9_heading,
                0,
                R.string.question9_option1,
                R.string.question9_option2,
                R.string.question9_option3,
                R.string.question9_option4,
                1,
                R.string.question9_information
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