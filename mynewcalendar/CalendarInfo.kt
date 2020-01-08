package com.example.mynewcalendar

import java.text.SimpleDateFormat
import java.util.*

class CalendarInfo(var month: Int, var year: Int) {

    private val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

    fun getDay(): Int{
        val calendar = Calendar.getInstance()
        calendar.set(year,month,1)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }
    fun getDaysNo(): Int{
        val calendar = Calendar.getInstance()
        calendar.set(year,month,1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
    fun getTitle(): String{
        var tt = Calendar.getInstance()
        tt.set(year,month,1)
        return formatter.format(tt.time)
    }
}