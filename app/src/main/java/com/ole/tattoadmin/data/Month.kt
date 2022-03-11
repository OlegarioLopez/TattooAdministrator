package com.ole.tattoadmin.data

data class Month(
    var year: Int = 0,
    var monthNumber: Int=0,
    var monthName: String = "",
    var days: MutableList<Day> = mutableListOf()
)