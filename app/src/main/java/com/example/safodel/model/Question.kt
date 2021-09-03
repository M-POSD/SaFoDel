package com.example.safodel.model

import com.example.safodel.R

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
                    1, "What is this?",
                R.drawable.epic1_image,
                "epic1",
                "epic2",
                "epic3",
                "epic4",
                1,
                "This the first version of epic 1 image"
            ))
            questions.add(Question(
                2, "What is this?",
                R.drawable.hand_sanitizer,
                "hand shaker",
                "hand sanitizer",
                "hand washer",
                "dont select me plz",
                2,
                "Hand sanitize. You must keep this word in mind"
            ))
            questions.add(Question(
                3, "What is this?",
                R.drawable.book,
                "book1",
                "no book",
                "notebook",
                "book",
                4,
                "BOOK ???"
            ))
            questions.add(Question(
                4, "What is my colour?",
                R.drawable.gloves,
                "gloves",
                "glove",
                "golves",
                "blue",
                4,
                "Select 'COLOUR' !! "
            ))
            questions.add(Question(
                5, "What is this?",
                R.drawable.headlight,
                "light",
                "lights",
                "headlight",
                "I am the answer",
                3,
                "I AM THE HEADLIGHT, PLZ REMEMBER ME!"
            ))


            return questions
        }
    }
}