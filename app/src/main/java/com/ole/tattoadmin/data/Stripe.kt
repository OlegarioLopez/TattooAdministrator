package com.ole.tattoadmin.data

import java.time.Duration
import java.time.LocalTime

data class Stripe (
   private var availability: Boolean = true,
    var bookedBy: String = "",
    var momentIni: LocalTime = LocalTime.of(-1,0),
    var momentFin: LocalTime = LocalTime.of(-1,0),
    var duration: Long = 0

){
    init {
        duration = Duration.between(momentIni, momentFin).toMinutes()
    }
}