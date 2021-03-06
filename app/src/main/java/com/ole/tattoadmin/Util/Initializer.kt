package com.ole.tattoadmin.Util

import androidx.compose.runtime.State
import com.ole.tattoadmin.domain.model.Day
import com.ole.tattoadmin.domain.model.Month
import com.ole.tattoadmin.domain.model.Spot
import java.time.LocalDate
import java.time.YearMonth

class Initializer(
    private var yearNumber: Int,
    private var monthNumber: Int,
    private var startMorning: List<Int>,
    private var finishMorning: List<Int>,
    private var startEvening: List<Int>,
    private var finishEvening: List<Int>,
    private var daysWork: Set<Int>
) {


    fun initializeMonth(): Month {

        val yearMonth = YearMonth.of(yearNumber, monthNumber)
        val name = yearMonth.month.name
        val days = yearMonth.lengthOfMonth()
        //fillDays()

        return Month(year = yearNumber, monthNumber = monthNumber, monthName = name, days = days)
    }


    fun initializeDays(): MutableList<Day> {

        val auxListDays: MutableList<Day> = mutableListOf()

        for (dayNumber in 1..YearMonth.of(yearNumber, monthNumber).lengthOfMonth()) {

            val dayDate = LocalDate.of(yearNumber, monthNumber, dayNumber)
            val dayName = dayDate.dayOfWeek.name
            val auxDay = Day(
                month = YearMonth.of(yearNumber, monthNumber).month.name,
                weekDay = dayName,
                dayInMonth = dayNumber,
                startMorning,
                finishMorning,
                startEvening,
                finishEvening
            )

            auxListDays.add(auxDay)
        }
        return auxListDays
    }

    private fun fillSpots(dayDate: LocalDate): List<Spot> {
        val auxListSpots: List<Spot>

        if (daysWork.contains(dayDate.dayOfWeek.value)) {
            auxListSpots = listOf(
                Spot(
                    month = dayDate.month.name,
                    dayInMonth = dayDate.dayOfMonth,
                    momentIni = startMorning,
                    momentFin = finishMorning
                ),
                Spot(
                    month = dayDate.month.name,
                    dayInMonth = dayDate.dayOfMonth,
                    momentIni = startEvening,
                    momentFin = finishEvening
                )
            )
        }else auxListSpots= listOf()
        return auxListSpots
    }

    fun initializeSpots(): MutableList<Spot> {
        var auxList: MutableList<Spot> = mutableListOf()
        for (dayNumber in 1..YearMonth.of(yearNumber, monthNumber).lengthOfMonth()){
           auxList.addAll(fillSpots(LocalDate.of(yearNumber,monthNumber,dayNumber)))
        }
        auxList.sortBy { it.dayInMonth }
        return auxList
    }
}