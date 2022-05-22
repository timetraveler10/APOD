package com.hussein.apod.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    fun longToDate(long: Long): Date {
        return Date(long)
    }

    fun isAfter6Am(): Boolean {

        val time = SimpleDateFormat("HH:mm")

        val currentTime: Date = time.parse(time.format(Date()))

        val secondDate: Date = time.parse("6:30")

        val cmp = currentTime.compareTo(secondDate)

        return cmp >= 0


    }

    fun compareDates(otherDay: Long): Boolean {

        val today: Date = sdf.parse(dateToString(createTimestamp()))
        val secondDate: Date = sdf.parse(dateToString(longToDate(otherDay)))

        val cmp = today.compareTo(secondDate)

        return cmp > 0
    }


    fun dateToLong(date: Date): Long {
        return date.time  // return seconds
    }

    // Ex: November 4, 2021
    fun dateToString(date: Date): String {
        return sdf.format(date)
    }

    fun stringToDate(string: String): Date {
        return sdf.parse(string)
            ?: throw NullPointerException("Could not convert date string to Date object.")
    }

    fun createTimestamp(): Date {
        return Date()
    }
}