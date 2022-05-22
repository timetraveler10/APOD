package com.hussein.apod.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*

fun MyDatePickerDialog(context:Context) {

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    var selectedDate = ""

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate = "$mDayOfMonth-${mMonth + 1}-$mYear"
        }, mYear, mMonth, mDay
    )

}