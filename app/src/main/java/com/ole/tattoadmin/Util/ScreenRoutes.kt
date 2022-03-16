package com.ole.tattoadmin.Util

sealed class ScreenRoutes(val route: String){
    object MainScreen: ScreenRoutes("main_screen")
    object SusccessScreen: ScreenRoutes("susccess_screen ")
    object SpotViewerScreen: ScreenRoutes("spot_viewer_screen")
    object SpotDetailScreen: ScreenRoutes("spot_detail_screen")
}
