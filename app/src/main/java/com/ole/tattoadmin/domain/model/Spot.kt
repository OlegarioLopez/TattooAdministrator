package com.ole.tattoadmin.domain.model

import java.time.Duration
import java.time.LocalTime

data class Spot (
    val month: String="",
    val dayInMonth: Int=0,
    var availability: Boolean = true,
    var bookedBy: String = "",
    var momentIni: List<Int> = listOf(0,0),
    var momentFin: List<Int> = listOf(0,0),
    var duration: Long = 0,
    var key: Int = 0

){
    private var momentIniTime: LocalTime? = LocalTime.of(0,0)
    private var momentFinTime: LocalTime? = LocalTime.of(0,0)
    var splitedFrom: Int = 0

    init {
        updateInternals()
    }

    override fun toString(): String {
        updateInternals()
        return "De $momentIniTime a $momentFinTime"
    }

    fun toStringShort(): String {
        updateInternals()
        return "$momentIniTime-$momentFinTime"
    }

    fun updateInternals(){
        momentIniTime = LocalTime.of(momentIni[0],momentIni[1])
        momentFinTime = LocalTime.of(momentFin[0],momentFin[1])
        duration = Duration.between(momentIniTime,momentFinTime).toMinutes()
        key="$dayInMonth${momentIni.get(0)}${momentIni.get(1)}".toInt()

    }

}