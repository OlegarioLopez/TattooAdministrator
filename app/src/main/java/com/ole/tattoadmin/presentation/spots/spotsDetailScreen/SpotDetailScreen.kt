package com.ole.tattoadmin.ui.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.presentation.spots.spotsDetailScreen.SpotDetailScreenViewModel
import com.ole.tattoadmin.presentation.spots.spotsViewer.SpotsViewerViewModel

@Composable
fun SpotDetailScreen(
    navController: NavHostController,
    spotsViewModel: SpotsViewerViewModel,
    detailViewModel: SpotDetailScreenViewModel
) {
    var text by remember {
        mutableStateOf("")
    }
    val currentSpot = spotsViewModel.currentSpot.value

    Column() {


        Column(
            Modifier
                .fillMaxWidth()
                .background(color = if (currentSpot.availability) Color.Green else Color.Red),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = currentSpot.dayInMonth.toString() + "," + currentSpot.month + "  ${currentSpot.toString()} \n duration: ${currentSpot.duration} \r\n ", // TODO PELIGRO no harcodear el año
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(25.dp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(10.0.dp))
        if (!spotsViewModel.currentSpot.value.availability) {
            Surface() {
            Row() {

                    OutlinedTextField(
                        value = text,
                        placeholder = { Text("¿A que día del mes quieres pasar la cita?") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { value ->
                            if (value.length <= 2) {
                                text = value.filter { it.isDigit() }
                            }
                        }
                    )
                    Button(onClick = {
                        val success = detailViewModel.changeSpot(
                            currentSpot,
                            currentSpot.copy(dayInMonth = text.toInt())
                        )
                        navController.popBackStack()              //if(detailViewModel.success.value) Toast.makeText(context,"Éxito en la modificación de la cita",Toast.LENGTH_LONG).show()
                        //else Toast.makeText(context,"No se ha podido modificar la cita, seleccione otro hueco al que mover la cita",Toast.LENGTH_LONG).show()
                    }) {

                    }
                }

            }

        }


    }
}