package com.ole.tattoadmin.presentation.spots

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.domain.model.Day
import com.ole.tattoadmin.domain.model.Spot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDate

class SpotsViewerViewModel : ViewModel() {

    private val daysCollectionRef = Firebase.firestore.collection("Days").orderBy("dayInMonth")

    private val spotsCollectionOrdered =
        Firebase.firestore.collection("Spots").orderBy("dayInMonth").orderBy("momentIni")

    // States
    private var _daysAvailables = mutableStateOf<List<Day>>(mutableListOf())
    var daysAvailables: State<List<Day>> = _daysAvailables

    private val _spotsAvailables = mutableStateOf<List<Spot>>(mutableListOf())
    var spotsAvailables: State<List<Spot>> = _spotsAvailables

    private val _currentSpot = mutableStateOf<Spot>(Spot())
    var currentSpot: State<Spot> = _currentSpot

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _spotsAvailables.value =  async {  retrieveSpots() }.await()
                _daysAvailables.value =  async{ retrieveDaysWithSpots() }.await()
            }

        }

    }

    //TODO Comprobar si se cargan las citas en orden por día/hora
    suspend fun retrieveSpots(): MutableList<Spot> {

        val auxSpotsAvailables: MutableList<Spot> = mutableListOf()

        val querySnapshot =
            spotsCollectionOrdered.whereEqualTo("month", LocalDate.now().month.name).get().await()

        for (document in querySnapshot.documents) {

            val spots = document.toObject<Spot>()
            spots?.let {
                auxSpotsAvailables.add(it)
            }
        }
        return auxSpotsAvailables


    }

    suspend fun retrieveDaysWithSpots(): MutableList<Day> {

        val auxDaysAvailables: MutableList<Day> = mutableListOf()

        val querySnapshot =
            daysCollectionRef.whereEqualTo("month", LocalDate.now().month.name).get().await()

        for (document in querySnapshot.documents) {

            val day = document.toObject<Day>()
            day?.let {
               if(day.weekDay != "SATURDAY" && day.weekDay != "SUNDAY") auxDaysAvailables.add(it)
            }
        }

        return auxDaysAvailables

    }

    fun saveCurrentSpot(spot: Spot) {

        viewModelScope.launch {
            withContext(Dispatchers.Main){
                _currentSpot.value = spot
                Log.d(TAG,"current spot seteado")
            }
        }

    }
}