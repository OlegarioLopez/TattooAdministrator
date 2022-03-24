package com.ole.tattoadmin.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ole.tattoadmin.presentation.spots.SpotsViewerViewModel
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun SpotDetailScreen(navController: NavHostController, spotsViewModel: SpotsViewerViewModel) {

    val currentSpot = spotsViewModel.currentSpot
    
    Column(
        Modifier.fillMaxWidth().background(color = if(currentSpot.value.availability) Color.Green else Color.Red),
    verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = currentSpot.value.dayInMonth.toString() + "," + currentSpot.value.month + "  ${currentSpot.value.toString()} \n duration: ${currentSpot.value.duration} \r\n ", // TODO PELIGRO no harcodear el año
            fontSize = 20.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(25.dp),
            textAlign = TextAlign.Center
        )

    }

}