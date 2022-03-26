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

class saveMonthDataViewModel : ViewModel() {

    private val monthsCollectionRef = Firebase.firestore.collection("Months")
    private val daysCollectionRef = Firebase.firestore.collection("Days")
    private val spotsCollectionRef = Firebase.firestore.collection("Spots")

    private var spanishLocale: Locale = Locale("es", "ES")
    var fmt: DateTimeFormatter = DateTimeFormatterBuilder() // case insensitive
        .parseCaseInsensitive() // pattern with full month name (MMMM)
        .toFormatter(Locale("es", "ES"))

    private var _isShiftSelected = mutableStateOf<Boolean>(false)
    val isShiftSelected: State<Boolean> = _isShiftSelected

    private var _numberShiftSelected = mutableStateOf<Int>(0)
    val numberShiftSelected: State<Int> = _numberShiftSelected


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

    fun isShiftSelected() {
        _isShiftSelected.value = !_isShiftSelected.value
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

    fun numberShiftSelected(currentShift: Int) {

        _numberShiftSelected.value = currentShift
    }
}