package com.example.safodel.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * data class to define one to many relationship between TimeEntry and QuizResult
 */
data class TimeEntryWithQuizResult(
    @Embedded val timeEntry: TimeEntry,
    @Relation(
        parentColumn = "uid",
        entityColumn = "time_entry_id"
    )
    val quizResults: List<QuizResult>
)
