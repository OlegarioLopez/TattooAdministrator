package com.ole.tattoadmin.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.Initializer
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel
import com.ole.tattoadmin.presentation.saveMonthData.dayCheckBox
import com.ole.tattoadmin.presentation.saveMonthData.shiftsCheck


//TODO Cambiar los mensajes de log por Toast, para ello hay que investigar como pasar un objeto context a la función
@Composable
fun SaveMonthData(navController: NavHostController) {
    //Default working hours in that month



    val viewModel = saveMonthDataViewModel()
    val nextMonth = viewModel.nextMonth
    val startTimeMorning: List<Int> = listOf(10, 0)
    val finishTimeMorning: List<Int> = listOf(14, 0)
    val startTimeEvening: List<Int> = listOf(17, 0)
    val finishTimeEvening: List<Int> = listOf(20, 0)
    val initializer =
        Initializer(
            2022,
            4,
            startTimeMorning,
            finishTimeMorning,
            startTimeEvening,
            finishTimeEvening
        )

    Box(modifier = Modifier
        .background(Color(0xFFf0f7ee))
        .fillMaxSize()) {
        Column {
            Text(text = "Vamos a preparar la planificación para " + nextMonth,
                fontSize = 24.sp)
            Spacer(modifier = Modifier.size(20.dp))

            Text(text = "¿Qué días trabajaras? (siempre podrás cerrar días que necesites de urgencia)")
            Spacer(modifier = Modifier.size(12.dp))

            Row(){
                Spacer(Modifier.width(10.dp))
                Card(
                    elevation = 12.dp
                ) {
                    LazyRow(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(Color(0xFF84dccf))
                        //.background(Color(0xFF0C120C))
                    ) {

                        items(

                            items = viewModel.listDays,
                            itemContent = { currentDay ->
                                dayCheckBox(day = currentDay,viewModel)

                            })
                    }

                }
            }

            Spacer(Modifier.height(15.dp))

            Text(text = "¿Cuántos turnos trabajaras al día?")
            Spacer(modifier = Modifier.size(12.dp))

            shiftsCheck(viewModel)
            Spacer(modifier = Modifier.size(12.dp))

            Row() {
                for(i in 0..viewModel.numberShiftSelected.value){
                    Card(elevation = 20.dp){
                        Text(text = i.toString())
                    }
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA79DCC)),
                onClick = {
                    val month = initializer.initializeMonth()
                    val days = initializer.initializeDays()
                    val Spots = initializer.initializeSpots()
                    viewModel.saveMonth(month)
                    viewModel.saveDays(days)
                    viewModel.saveSpots(Spots)
                    navController.navigate(ScreenRoutes.SusccessScreen.route
                    )
                },
            ) {
                Text(text = "Guardar esta planificacion para el próximo mes")
            }
        }
    }


}


