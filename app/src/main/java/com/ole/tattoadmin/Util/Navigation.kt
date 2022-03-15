package com.ole.tattoadmin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.ui.Screens.SaveMonthData
import com.ole.tattoadmin.ui.Screens.Success


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoutes.MainScreen.route ){
        composable( route = ScreenRoutes.MainScreen.route){
            SaveMonthData(navController)
        }
        composable(route = ScreenRoutes.SusccessScreen.route){
            Success()
        }
    }
}
