package com.sky.bet.horses

import com.sky.bet.horses.model.RaceSummary
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ModelUnitTests {
    @Test
    fun summary_countdown_returns_time_on_same_day() {
        val summary = RaceSummary("1", "", SimpleDateFormat("dd-MM-yyyy").format(Date()), "", "", false, false, "", "", 0, "11:00")
        Assert.assertEquals(summary.time, summary.getCountdown())
    }

    @Test
    fun datetime_parses_successfully(){
        val date = Date()
        date.seconds = 0
        val summary = RaceSummary("1", "", SimpleDateFormat("yyyy-MM-dd").format(date),
            "", "", false, false, "", "", 0, SimpleDateFormat("HH:mm").format(date))
        Assert.assertEquals(date.toString(),summary.getDateTime().toString())
    }
}