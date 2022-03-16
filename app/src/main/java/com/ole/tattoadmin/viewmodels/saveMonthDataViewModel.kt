package com.ole.tattoadmin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.data.Day
import com.ole.tattoadmin.data.Month
import com.ole.tattoadmin.data.Spot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class saveMonthDataViewModel : ViewModel() {

    private val monthsCollectionRef = Firebase.firestore.collection("Months")
    private val daysCollectionRef = Firebase.firestore.collection("Days")
    private val spotsCollectionRef = Firebase.firestore.collection("Spots")

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
}