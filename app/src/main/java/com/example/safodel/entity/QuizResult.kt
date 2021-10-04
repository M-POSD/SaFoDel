package com.example.safodel.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data class to define the quiz_result entity
 */
@Entity(tableName = "quiz_result")
data class QuizResult(
    @ColumnInfo(name = "time_entry_id") var timeEntryId: Int,
    @ColumnInfo(name = "question_heading") var question_heading: Int,
    @ColumnInfo(name = "question_info") var question_info: Int,
    @ColumnInfo(name = "is_correct") var isCorrect: Boolean,
    @PrimaryKey(autoGenerate = true) var uid: Int=0
)
