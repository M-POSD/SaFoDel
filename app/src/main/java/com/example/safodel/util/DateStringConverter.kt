package com.example.safodel.util

import java.lang.RuntimeException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateStringConverter {
    fun parseDateToStr(format: String?, date: Date?): String {
        return SimpleDateFormat(format).format(date)
    }

    fun parseStrToDate(format: String?, date: String?): Date {
        return try {
            SimpleDateFormat(format).parse(date)
        } catch (pe: ParseException) {
            throw RuntimeException()
        }
    }
}