package com.ole.tattoadmin.ui.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.Initializer
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel
import com.ole.tattoadmin.presentation.saveMonthData.components.timePicker
import com.ole.tattoadmin.presentation.saveMonthData.dayCheckBox
import java.time.LocalDate


//TODO Cambiar los mensajes de log por Toast, para ello hay que investigar como pasar un objeto context a la función


@Composable
fun SaveMonthData(
    context: Context,
    navController: NavHostController,
    saveMonthDataViewModel: saveMonthDataViewModel
) {
    //Default working hours in that month


    val viewModel = remember() {
        saveMonthDataViewModel
    }
    val nextMonth = viewModel.nextMonth

    Box(
        modifier = Modifier
            .background(Color(0xFFf0f7ee))
            .fillMaxSize()
    ) {
        Column {
            Text(
                text = "Vamos a preparar la planificación para " + nextMonth,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.size(20.dp))

            Text(text = "¿Qué días trabajaras? (siempre podrás cerrar días que necesites de urgencia)")
            Spacer(modifier = Modifier.size(12.dp))

            Row() {
                Spacer(Modifier.width(10.dp))
                Card(
                    elevation = 12.dp
                ) {
                    var dayNumber = 1
                    LazyRow(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(Color(0xFF84dccf))
                        //.background(Color(0xFF0C120C))
                    ) {

                        items(

                            items = viewModel.listDays,
                            itemContent = { currentDay ->
                                dayCheckBox(day = currentDay, dayNumber, viewModel)
                                dayNumber++
                            })
                    }

                }
            }

            Spacer(Modifier.height(15.dp))

            Spacer(modifier = Modifier.size(12.dp))

            Column {
                Row {

                    Text(text = "¿Inicio turno de mañana?")
                    timePicker(context = context, viewModel = viewModel)

                }
                if (viewModel.shiftsTimeFilled.value > 0) {
                    Row {
                        Text(text = "¿ Fin turno de mañana?")
                        timePicker(context = context, viewModel = viewModel)
                    }
                }
                if (viewModel.shiftsTimeFilled.value > 1) {
                    Row {
                        Text(text = "¿ Inicio turno de tarde?")
                        timePicker(context = context, viewModel = viewModel)
                    }
                }
                if (viewModel.shiftsTimeFilled.value > 2) {
                    Row {
                        Text(text = "¿ Fin turno de tarde?")
                        timePicker(context = context, viewModel = viewModel)
                    }
                }

            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA79DCC)),
                onClick = {
                    if (viewModel.shiftsTimeFilled.value == 4) {
                        val initializer =
                            Initializer(
                                startMorning = viewModel.listOfShifts.value[0],
                                finishMorning = viewModel.listOfShifts.value[1],
                                startEvening = viewModel.listOfShifts.value[2],
                                finishEvening = viewModel.listOfShifts.value[3],
                                monthNumber = LocalDate.now().monthValue,
                                yearNumber = LocalDate.now().year,
                                daysWork = viewModel.daysSelected.value
                            )
                        val month = initializer.initializeMonth()
                        val days = initializer.initializeDays()
                        val Spots = initializer.initializeSpots()
                        print(viewModel.daysSelected.value)
                        viewModel.saveMonth(month)
                         viewModel.saveDays(days)
                         viewModel.saveSpots(Spots)
                        navController.navigate(
                            ScreenRoutes.SusccessScreen.route
                        )
                    } else {
                        print(viewModel.daysSelected.value)
                        Toast.makeText(
                            context,
                            "Debe introducir todos los datos" + viewModel.daysSelected.value,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
            ) {
                Text(text = "Guardar esta planificacion para el próximo mes")
            }
        }
    }


}

