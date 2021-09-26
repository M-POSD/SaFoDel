package com.example.safodel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * data class to define the time_entry entity
 */
@Entity(tableName = "time_entry")
data class TimeEntry(
    var time: Date?,
    @PrimaryKey(autoGenerate = true) var uid: Int=0
)
