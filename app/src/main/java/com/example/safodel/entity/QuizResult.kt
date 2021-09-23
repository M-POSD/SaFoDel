package com.example.safodel.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "quiz_result")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) var uid: Int=0,
    @ColumnInfo(name = "time_entry") var timeEntry: Date?,
    @ColumnInfo(name = "question_heading") var question_heading: String,
    @ColumnInfo(name = "question_info") var question_info: String,
    @ColumnInfo(name = "is_correct") var isCorrect: Boolean
)
