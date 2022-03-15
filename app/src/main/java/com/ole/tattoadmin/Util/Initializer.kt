package com.ole.tattoadmin.Util

import com.ole.tattoadmin.data.Day
import com.ole.tattoadmin.data.Month
import com.ole.tattoadmin.data.Stripe
import java.time.LocalDate
import java.time.YearMonth

class Initializer(
    private var yearNumber: Int,
    private var monthNumber: Int,
    private var startMorning: List<Int>,
    private var finishMorning: List<Int>,
    private var startEvening: List<Int>,
    private var finishEvening: List<Int>
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

    private fun fillStripes(dayDate: LocalDate): List<Stripe> {
        val auxListStripes: List<Stripe>

        if (dayDate.dayOfWeek.value != 6 && dayDate.dayOfWeek.value != 7) {
            auxListStripes = listOf(
                Stripe(
                    month = dayDate.month.name,
                    dayInMonth = dayDate.dayOfMonth,
                    momentIni = startMorning,
                    momentFin = finishMorning
                ),
                Stripe(
                    month = dayDate.month.name,
                    dayInMonth = dayDate.dayOfMonth,
                    momentIni = startEvening,
                    momentFin = finishEvening
                )
            )
        }else auxListStripes= listOf()
        return auxListStripes
    }

    fun initializeStripes(): MutableList<Stripe> {
        var auxList: MutableList<Stripe> = mutableListOf()
        for (dayNumber in 1..YearMonth.of(yearNumber, monthNumber).lengthOfMonth()){
           auxList.addAll(fillStripes(LocalDate.of(yearNumber,monthNumber,dayNumber)))
        }
        auxList.sortBy { it.dayInMonth }
        return auxList
    }
}