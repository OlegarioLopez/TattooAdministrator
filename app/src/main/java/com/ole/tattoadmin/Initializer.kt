package com.ole.tattoadmin

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


    fun initializeMonth():Month{

        val name = YearMonth.of(yearNumber, monthNumber).month.name
        val days = fillDays()

        return Month(year = yearNumber,monthNumber=monthNumber, monthName = name, days = days)
    }


    private fun fillDays(): MutableList<Day> {

        val auxListDays:MutableList<Day> = mutableListOf()

        for( dayNumber in 1..YearMonth.of(yearNumber, monthNumber).lengthOfMonth()){

            val dayDate = LocalDate.of(yearNumber,monthNumber,dayNumber)
            val dayName = dayDate.dayOfWeek.name
            val dayStripes = fillStripes(dayDate)

            val auxDay=Day(weekDay = dayName, dayInMonth = dayNumber, startMorning,finishMorning,startEvening,finishEvening)
            auxDay.stripes = dayStripes
            auxListDays.add(auxDay)
        }
        return auxListDays
    }

    private fun fillStripes(dayDate: LocalDate): MutableList<Stripe> {
        val auxListStripes:MutableList<Stripe> = mutableListOf()

        if (dayDate.dayOfWeek.value != 6 && dayDate.dayOfWeek.value != 7 ){
            val auxStripeMorning = Stripe(momentIni= startMorning, momentFin = finishMorning )
            val auxStripeEvening = Stripe(momentIni = startEvening, momentFin = finishEvening)
            auxListStripes.add(auxStripeMorning)
            auxListStripes.add(auxStripeEvening)
        }
        return auxListStripes
    }
}