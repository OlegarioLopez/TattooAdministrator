package com.ole.tattoadmin

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ole.tattoadmin.Util.ScreenRoutes
import com.ole.tattoadmin.presentation.home.Home
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel
import com.ole.tattoadmin.presentation.spots.SpotsViewerViewModel
import com.ole.tattoadmin.ui.Screens.SaveMonthData
import com.ole.tattoadmin.ui.Screens.SpotDetailScreen
import com.ole.tattoadmin.ui.Screens.SpotsViewer
import com.ole.tattoadmin.ui.Screens.Success


@Composable
fun Navigation(context: Context) {
    var spotsViewModel = SpotsViewerViewModel()
    val saveMonthDataViewModel = saveMonthDataViewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoutes.Home.route ){
        composable( route = ScreenRoutes.Home.route){
           Home(navController)
        }
        composable(route = ScreenRoutes.SusccessScreen.route){
            Success()
        }
        composable(route = ScreenRoutes.SpotViewerScreen.route){
            SpotsViewer(navController,spotsViewModel)
        }
        composable(route = ScreenRoutes.SaveMonthData.route){
            SaveMonthData(context,navController,saveMonthDataViewModel)
        }
        composable(route = ScreenRoutes.SpotDetailScreen.route){
            SpotDetailScreen(navController,spotsViewModel)
        }
    }
}
