package com.ole.tattoadmin.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ole.tattoadmin.Util.ScreenRoutes

@Composable
fun Home(navController: NavHostController) {

    Column() {
        Button(
            onClick = {
                navController.navigate(ScreenRoutes.SaveMonthData.route)
            },
        ) {
            Text(text = "Planificar el próximo mes")
        }
        Button(
            onClick = {
                navController.navigate(ScreenRoutes.SpotViewerScreen.route)
            },
        ) {
            Text(text = "Ver/Modificar citas")
        }
    }
}