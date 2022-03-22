package com.ole.tattoadmin.data

import android.content.ContentValues.TAG
import android.util.Log
import java.time.Duration
import java.time.LocalTime

data class Spot(
    val month: String = "",
    val dayInMonth: Int = 0,
    var availability: Boolean = true,
    var bookedBy: String = "",
    var momentIni: List<Int> = listOf(0, 0),
    var momentFin: List<Int> = listOf(0, 0),
    var duration: Long = 0,
    var key: Int = 0,
) {
    var splitedFrom: Int = 0
    private var momentIniTime: LocalTime? = LocalTime.of(0, 0)
    private var momentFinTime: LocalTime? = LocalTime.of(0, 0)

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

     fun updateInternals() {
        momentIniTime = LocalTime.of(momentIni[0], momentIni[1])
        momentFinTime = LocalTime.of(momentFin[0], momentFin[1])
        duration = Duration.between(momentIniTime, momentFinTime).toMinutes()
        key = "$dayInMonth${momentIni[0]}${momentIni[1]}".toInt()
    }

    fun isIncludedIn(spot: Spot): Boolean {

            if (LocalTime.of(this.momentIni[0],this.momentFin[1]) >= spot.momentIniTime && LocalTime.of(this.momentFin[0],this.momentFin[1]) <= spot.momentFinTime) {
                return true

        }

        return false
    }

    fun split(finalSpot: Spot): List<Spot> {
        Log.d(TAG,"splitting")// Sólo usar esta función cuando se haya comprobado previamente que el spot final está incluido en el spot this
        var auxListSpots: MutableList<Spot> = mutableListOf()
        var auxSpot: Spot = this.copy(momentFin = finalSpot.momentIni)
        auxSpot.updateInternals()
        if(auxSpot.duration > 0) auxListSpots.add(auxSpot)
        auxSpot = Spot(finalSpot.month,finalSpot.dayInMonth,finalSpot.availability,finalSpot.bookedBy,finalSpot.momentIni,finalSpot.momentFin)
        auxListSpots.add(auxSpot)
        auxSpot=this.copy(momentIni = finalSpot.momentFin, momentFin = this.momentFin)
        auxSpot.updateInternals()
        if(auxSpot.duration > 0)  auxListSpots.add(auxSpot)

        return auxListSpots.toList()
    }
}