package com.ole.tattoadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ole.tattoadmin.data.Month
import com.ole.citastatto.ui.theme.CitasTattoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalTime

class MainActivity : ComponentActivity() {

    private val monthsCollectionRef = Firebase.firestore.collection("Months")
    //Default working hours in that month
    val startTimeMorning: LocalTime = LocalTime.of(10,0)
    val finishTimeMorning: LocalTime = LocalTime.of(14,0)
    val startTimeEvening: LocalTime = LocalTime.of(17,0)
    val finishTimeEvening: LocalTime = LocalTime.of(20,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasTattoTheme {
                ButtonAppointment()
            }
        }
    }

     private fun saveMonth(month: Month) = CoroutineScope(Dispatchers.IO).launch {
        try {
            monthsCollectionRef.add(month).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "éxito en el guardado en firestore",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    @Composable
    fun ButtonAppointment() {
        var initializer=Initializer(2022,4,startTimeMorning,finishTimeMorning,startTimeEvening,finishTimeEvening)
        Button(
            onClick = {
                val month= initializer.initializeMonth()
                saveMonth(month)
            },
        ) {
            Text(text = "Botón")
        }
    }

}


