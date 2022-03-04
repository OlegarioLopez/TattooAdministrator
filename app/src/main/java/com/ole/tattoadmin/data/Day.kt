package com.ole.tattoadmin.data

import java.time.LocalTime

data class Day(
    val weekDay: String,
    val dayInMonth: Int,
    val startTimeMorning: LocalTime? = LocalTime.of(-1,0),
    val finishTimeMorning: LocalTime? = LocalTime.of(-1,0),
    val startTimeEvening: LocalTime? = LocalTime.of(-1,0),
    val finishTimeEvening: LocalTime? = LocalTime.of(-1,0),
    var stripes: MutableList<Stripe> = mutableListOf()
)