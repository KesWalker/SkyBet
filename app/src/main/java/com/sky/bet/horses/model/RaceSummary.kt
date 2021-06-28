package com.sky.bet.horses.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class RaceSummary(
    val age: String,
    val course_name: String,
    val date: String,
    val distance: String,
    val going: String,
    val has_handicap: Boolean,
    val hidden: Boolean,
    val name: String,
    val race_stage: String,
    val ride_count: Int,
    val time: String,
){
    fun getCountdown() : String {
        val raceDate = getDateTime()
        val difference = raceDate.time - Date().time
        val daysDifference = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS)
        if(daysDifference < 1){
            return time
        }else{
            return "${daysDifference} days away"
        }
    }

    fun getDateTime() : Date{
        return SimpleDateFormat("yyyy-MM-dd HH:mm").parse("${date} ${time}")
    }
}