package com.ole.tattoadmin.ui.Screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.Initializer
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.viewmodels.saveMonthDataViewModel


//TODO Cambiar los mensajes de log por Toast, para ello hay que investigar como pasar un objeto context a la función
@Composable
fun SaveMonthData(navController: NavHostController) {
    //Default working hours in that month

    val viewModel = saveMonthDataViewModel()
    val startTimeMorning: List<Int> = listOf(10,0)
    val finishTimeMorning: List<Int> = listOf(14,0)
    val startTimeEvening: List<Int> = listOf(17,0)
    val finishTimeEvening: List<Int> = listOf(20,0)
    val initializer=
        Initializer(2022,4,startTimeMorning,finishTimeMorning,startTimeEvening,finishTimeEvening)
    Button(
        onClick = {
            val month= initializer.initializeMonth()
            val days= initializer.initializeDays()
            val stripes = initializer.initializeStripes()
            viewModel.saveMonth(month)
            viewModel.saveDays(days)
            viewModel.saveStripes(stripes)
            navController.navigate(ScreenRoutes.SusccessScreen.route)
        },
    ) {
        Text(text = "Botón")
    }
}


