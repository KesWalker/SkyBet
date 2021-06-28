package com.sky.bet.horses.model

data class Race(
    val race_summary: RaceSummary,
    val rides: List<Ride>,
    var image: Int
)