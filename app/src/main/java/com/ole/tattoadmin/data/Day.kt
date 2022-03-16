package com.ole.tattoadmin.data

import java.time.LocalTime

data class Day(
    var month: String ="",
    var weekDay: String="",
    var dayInMonth: Int=0,
    var startMorning: List<Int> = listOf(0,0),
    var finishMorning: List<Int> = listOf(0,0),
    var startEvening: List<Int> = listOf(0,0),
    var finishtEvening: List<Int> = listOf(0,0),
){
    private var startMorningTime: LocalTime? = LocalTime.of(0,0)
    private var finishMorningTime: LocalTime? = LocalTime.of(0,0)
    private var startEveningTime: LocalTime? = LocalTime.of(0,0)
    private var finishtEveningTime: LocalTime? = LocalTime.of(0,0)
   init{
       startMorningTime=LocalTime.of(startMorning[0],startMorning[1])
       finishMorningTime=LocalTime.of(finishMorning[0],finishMorning[1])
       startEveningTime=LocalTime.of(startEvening[0],startEvening[1])
       finishtEveningTime=LocalTime.of(finishtEvening[0],finishtEvening[1])


   }

}