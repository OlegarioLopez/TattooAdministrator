package com.ole.tattoadmin.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.domain.model.Day
import com.ole.tattoadmin.domain.model.Month
import com.ole.tattoadmin.domain.model.Spot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.util.*
import kotlin.math.min

class saveMonthDataViewModel : ViewModel() {

    private val monthsCollectionRef = Firebase.firestore.collection("Months")
    private val daysCollectionRef = Firebase.firestore.collection("Days")
    private val spotsCollectionRef = Firebase.firestore.collection("Spots")

    private var spanishLocale: Locale = Locale("es", "ES")

    private val _listOfShifts = mutableStateOf(listOf(mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>()))
    val listOfShifts: State<List<List<Int>>> = _listOfShifts

    private val _shiftsTimeFilled = mutableStateOf(0)
    val shiftsTimeFilled: State<Int> = _shiftsTimeFilled

    private val _daysSelected = mutableStateOf(mutableSetOf<Int>())
    val daysSelected: State<Set<Int>> = _daysSelected

    private var currentDate =
        LocalDate.now()
    val listDays =
        arrayListOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val nextMonth =
        LocalDate.of(
            currentDate.year,
            currentDate.monthValue + 1,
            currentDate.dayOfMonth
        ).month.getDisplayName(TextStyle.FULL, spanishLocale)
//TODO hacer esta variable un estado con remember

    fun saveMonth(month: Month) = CoroutineScope(Dispatchers.IO).launch {
        try {
            monthsCollectionRef.add(month).await()
            withContext(Dispatchers.Main) {
                Log.d(
                    "saveDays", "éxito en el guardado en firestore"
                )
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let {
                    Log.d(
                        "saveDays", it
                    )
                }
            }
        }
    }

    fun saveSpots(spots: MutableList<Spot>) = CoroutineScope(Dispatchers.IO).launch {
        try {
            for (spot in spots) {
                spotsCollectionRef.add(spot).await()
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let {
                    Log.d(
                        "saveDays", it
                    )
                }
            }
        }
    }

    fun saveDays(days: MutableList<Day>) = CoroutineScope(Dispatchers.IO).launch {
        try {
            for (day in days) {
                daysCollectionRef.add(day).await()

            }
            withContext(Dispatchers.Main) {
                Log.d(
                    "saveDays", "éxito en el guardado en firestore"
                )
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let {
                    Log.d(
                        "saveDays", it
                    )
                }
            }
        }
    }

    fun addShift() {
        if(_shiftsTimeFilled.value <5)  _shiftsTimeFilled.value++
    }

    fun fillShiftTime(shiftNumber: Int, hour: Int, minute: Int) {

        if(_listOfShifts.value.get(shiftNumber).isEmpty() && _shiftsTimeFilled.value < 5) {
            _listOfShifts.value.get(shiftNumber).add(hour)
            _listOfShifts.value.get(shiftNumber).add(minute)
        }
        else{
            _listOfShifts.value.get(shiftNumber)[0] = hour
            _listOfShifts.value.get(shiftNumber)[1] = minute
        }
    }


    fun addDay(dayCheckboxNumber: Int) {
_daysSelected.value.add((dayCheckboxNumber))
    }

    fun deleteDay(dayCheckboxNumber: Int) {
        _daysSelected.value.remove(dayCheckboxNumber)
    }


}