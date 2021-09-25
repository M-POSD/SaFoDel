package com.example.safodel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "time_entry")
data class TimeEntry(
    var time: Date?,
    @PrimaryKey(autoGenerate = true) var uid: Int=0
)
