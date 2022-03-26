package com.ole.tattoadmin.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.ScreenRoutes

@Composable
fun Home(navController: NavHostController) {

    Box(modifier =Modifier.background(Color(0xFFf0f7ee)).fillMaxSize()) {
        Column{
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA79DCC)),
                onClick = {
                    navController.navigate(ScreenRoutes.SaveMonthData.route)
                },
            ) {
                Text(text = "Planificar el próximo mes")
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA79DCC)),
                onClick = {
                    navController.navigate(ScreenRoutes.SpotViewerScreen.route)
                },
            ) {
                Text(text = "Ver/Modificar citas")
            }
        }
    }

}