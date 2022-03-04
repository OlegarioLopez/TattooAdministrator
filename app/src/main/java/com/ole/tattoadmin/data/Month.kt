package com.ole.tattoadmin.data

data class Month(
    var monthName: String,
    var days: MutableList<Day> = mutableListOf()
)