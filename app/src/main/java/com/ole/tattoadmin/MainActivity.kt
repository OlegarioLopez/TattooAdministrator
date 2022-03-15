package com.ole.tattoadmin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ole.citastatto.ui.theme.CitasTattoTheme
import com.ole.tattoadmin.data.Day
import com.ole.tattoadmin.data.Month
import com.ole.tattoadmin.data.Stripe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val monthsCollectionRef = Firebase.firestore.collection("Months")
    private val daysCollectionRef = Firebase.firestore.collection("Days")
    private val stripesCollectionRef = Firebase.firestore.collection("Stripes")

    //Default working hours in that month
    val startTimeMorning: List<Int> = listOf(10,0)
    val finishTimeMorning: List<Int> = listOf(14,0)
    val startTimeEvening: List<Int> = listOf(17,0)
    val finishTimeEvening: List<Int> = listOf(20,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitasTattoTheme {
                SaveMonthData()
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
    fun SaveMonthData() {
        val initializer=Initializer(2022,4,startTimeMorning,finishTimeMorning,startTimeEvening,finishTimeEvening)
        Button(
            onClick = {
                val month= initializer.initializeMonth()
                val days= initializer.initializeDays()
                val stripes = initializer.initializeStripes()
                saveMonth(month)
                saveDays(days)
                saveStripes(stripes)
            },
        ) {
            Text(text = "Botón")
        }
    }

    private fun saveStripes(stripes: MutableList<Stripe>) = CoroutineScope(Dispatchers.IO).launch {
        try {
            for(stripe in stripes){
                stripesCollectionRef.add(stripe).await()

            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveDays(days: MutableList<Day>)= CoroutineScope(Dispatchers.IO).launch {
        try {
            for(day in days){
                daysCollectionRef.add(day).await()

            }
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

}


